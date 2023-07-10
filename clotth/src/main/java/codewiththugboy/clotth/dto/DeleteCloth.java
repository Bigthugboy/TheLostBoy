package codewiththugboy.clotth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCloth {
    private String message;
    private boolean isDeleted;
    private LocalDateTime dateDeleted;



}
