package codewiththugboy.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateCustomerDto {
    private String firstName;
    private String lastName;
    private String PhoneNumber;
    private String Password;
    private String CreatedBy;

    public String getFirstName() {
        return firstName;
    }
}
