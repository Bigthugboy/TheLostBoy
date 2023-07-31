package codewiththugboy.clotth.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse implements Serializable {
    private String message;
    private Boolean isSuccessfully;
    private Object Data;
    private int result;
}
