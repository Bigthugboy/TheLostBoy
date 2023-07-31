package codewiththugboy.customer.token;




import codewiththugboy.customer.data.model.ConfirmationToken;
import codewiththugboy.customer.data.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class ConfirmationTokenImpl implements ConfirmationTokenInterface {
    private final Map<String, ConfirmationToken> tokenMap;

    public String generateAndSaveToken(Customer customer) {
        String token = String.valueOf(UUID.randomUUID().getLeastSignificantBits()).substring(1, 5);
        log.info("SavedToken -> {}",token);
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), customer);
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setEnabled(true);
        log.info("confirmToken -> {}",confirmationToken.getToken());
        tokenMap.put(token,confirmationToken);
        log.info("SavedToken -> {}",token);
        log.info("confirmToken -> {}",confirmationToken.getToken());
        return token;
    }



    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenMap.entrySet().removeIf(entry -> entry.getValue().getExpiredAt().isBefore(now));
    }

    @Override
    public boolean isEnabled(String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        if (confirmationToken.getToken().matches(getSavedToken(token))) {
            log.info("ConfirmToken -> {}",confirmationToken.getToken());
            Customer customer = new Customer();
            customer.setEnabled(true);
            log.info("isEnabled -> {}",customer.isEnabled());

            }else
                return false;
            // Do something with the customer or update the customer status based on the confirmation token

        return false;
    }



    @Override
    public String getSavedToken(String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        if (token.equals(confirmationToken.getToken())) {
            return token;
        }
        return null;
    }
//        log.info("ConfirmToken -> {}",token);
//        return String.valueOf(tokenMap.get(confirmationToken.getToken()));


}
