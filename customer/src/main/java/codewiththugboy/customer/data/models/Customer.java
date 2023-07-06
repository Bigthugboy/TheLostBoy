package codewiththugboy.customer.data.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private String fistName;
    private String lastName;
    private String email;
    private String PhoneNumber;


}
