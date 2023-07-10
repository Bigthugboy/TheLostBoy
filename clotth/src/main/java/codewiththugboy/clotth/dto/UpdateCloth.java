package codewiththugboy.clotth.dto;

import codewiththugboy.clotth.data.model.Cloth;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpdateCloth {
    private Cloth cloth;
    private Long id;
}
