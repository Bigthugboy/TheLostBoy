package codewiththugboy.clotth.dto;

import codewiththugboy.clotth.data.model.Cloth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateCloth {
    private Cloth cloth;
}
