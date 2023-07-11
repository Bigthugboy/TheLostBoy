package codewiththugboy.clotth.dto;

import codewiththugboy.clotth.data.model.Cloth;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpdateCloth {
    private Cloth cloth;
    private Long id;
    private LocalDateTime dateUpdated;
}
