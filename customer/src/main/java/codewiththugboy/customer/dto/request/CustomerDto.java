package codewiththugboy.customer.dto.request;

import codewiththugboy.customer.data.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Address address;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}
