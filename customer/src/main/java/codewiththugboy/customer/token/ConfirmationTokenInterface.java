package codewiththugboy.customer.token;

import codewiththugboy.customer.data.model.Customer;

public interface ConfirmationTokenInterface {
    boolean isEnabled(String token);

    String getSavedToken(String token);
    String generateAndSaveToken(Customer customer);

}
