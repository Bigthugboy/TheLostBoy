package codewiththugboy.clotth.dto.request;

import codewiththugboy.clotth.data.model.ClothSize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateRequest {

    private Long clothId;
    private String CollectionName;
    private ClothSize clothSize;
    private double price;
    private String clothMaterial;
    private String clothType;
    private String clothStyle;

}
