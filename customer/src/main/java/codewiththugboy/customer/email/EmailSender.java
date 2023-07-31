package codewiththugboy.customer.email;



import codewiththugboy.customer.dto.request.VerificationMessageRequest;
import codewiththugboy.customer.dto.response.MailResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<MailResponse> sendHtmlMail(VerificationMessageRequest messageRequest)throws UnirestException; ;
   CompletableFuture<MailResponse> sendSimpleMail(VerificationMessageRequest messageRequest) throws UnirestException;

}
