package codewiththugboy.customer.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private String status;
    private String message;
    private int result;
    private Object data;
}
