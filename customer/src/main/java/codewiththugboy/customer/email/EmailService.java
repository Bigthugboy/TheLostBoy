package codewiththugboy.customer.email;



import codewiththugboy.customer.dto.request.VerificationMessageRequest;
import codewiththugboy.customer.dto.response.MailResponse;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service("mailgun_sender")
@NoArgsConstructor
@Slf4j
public class EmailService implements EmailSender {
    private final String DOMAIN = System.getenv("MAILGUN_DOMAIN");
    private final String PRIVATE_KEY = System.getenv("MAILGUN_PRIVATE_KEY");

    @Override
    @Async
    public CompletableFuture<MailResponse> sendHtmlMail(VerificationMessageRequest messageRequest) throws UnirestException {
        com.mashape.unirest.http.HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", PRIVATE_KEY)
                .queryString("from", messageRequest.getSender())
                .queryString("to", messageRequest.getReceiver())
                .queryString("subject", messageRequest.getSubject())
                .queryString("html", messageRequest.getBody())
                .asString();

        log.info("Message sent successfully");
        MailResponse mailResponse = request.getStatus() == 200 ? new MailResponse(true) : new MailResponse(false);
        return CompletableFuture.completedFuture(mailResponse);
    }
    @Override
    @Async
    public CompletableFuture<MailResponse> sendSimpleMail(VerificationMessageRequest messageRequest) {
        String url = "https://api.mailgun.net/v3/" + DOMAIN + "/messages";

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                    .basicAuth("api", PRIVATE_KEY)
                    .field("from", messageRequest.getSender())
                    .field("to", messageRequest.getReceiver())
                    .field("subject", messageRequest.getSubject())
                    .field("text", messageRequest.getBody())
                    .asJson();

            if (response.getBody().getObject().getBoolean(PRIVATE_KEY)) {
                log.info("mailgunResponse ->{}", response);
                System.out.println("Email sent successfully!");
                return CompletableFuture.completedFuture(new MailResponse(true));
            } else {
                System.err.println("Failed to send email. Status: " + response.getStatus());
                System.err.println("Error Message: " + response.getBody().getObject().getString("message"));
            }
        } catch (UnirestException e) {
            System.err.println("Error occurred while sending email: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }

        return CompletableFuture.completedFuture(new MailResponse(false));
    }
}




