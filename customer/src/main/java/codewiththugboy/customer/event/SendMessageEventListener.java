package codewiththugboy.customer.event;


import codewiththugboy.customer.dto.request.VerificationMessageRequest;
import codewiththugboy.customer.email.EmailService;
import codewiththugboy.customer.sms.SmsSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;


@Component
@Slf4j
@AllArgsConstructor
public class SendMessageEventListener {
    private final SmsSender smsSender;
    private final EmailService emailService;




    @EventListener
    public void handleSendMessageEvent(SendMessageEvent event) throws ExecutionException, InterruptedException {
        VerificationMessageRequest messageRequest = (VerificationMessageRequest) event.getSource();

        String verificationLink = messageRequest.getVerificationToken() + "api/v1/auth/verify/" + messageRequest.getVerificationToken();

        log.info("Message request --> {}", messageRequest);

//        if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
//            log.info("Message Event -> {}", event.getSource());
//
//            MailResponse mailResponse = emailService.sendHtmlMail(messageRequest).get();
//            log.info("Mail Response --> {}", mailResponse);
//        } else

        log.info("verification_token -> {}", verificationLink);
        messageRequest.setBody(verificationLink);
        smsSender.sendSms(messageRequest.getPhoneNumber(), messageRequest.getVerificationToken());
        emailService.sendSimpleMail(messageRequest).get();

    }
}

