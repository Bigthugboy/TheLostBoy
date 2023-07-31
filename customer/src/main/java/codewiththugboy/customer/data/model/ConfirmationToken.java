package codewiththugboy.customer.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Boolean enabled;


    public ConfirmationToken(String token, LocalDateTime now, LocalDateTime expiredAt, Customer customer) {


    }
}
