package codewiththugboy.customer.sms;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class SmsSender {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    public void sendSms(String recipient, String message) {
        Twilio.init(accountSid, authToken);

        try {
            Message twilioMessage = Message.creator(
                            new PhoneNumber(recipient),
                            new PhoneNumber(phoneNumber),
                            message + " this is your verification token")
                    .create();

            System.out.println("SMS sent successfully! SID: " + twilioMessage.getSid());
        } catch (Exception e) {
            System.out.println("Failed to send SMS. Error: " + e.getMessage());
        }
    }
}