package codewiththugboy.clotth.dto.request;

import codewiththugboy.clotth.data.model.ClothSize;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpdateRequest {

    private String clothId;
    private String collectionName;
    private ClothSize clothSize;
    private double price;
    private String clothMaterial;
    private String clothType;
    private String clothStyle;
    private String designerName;


}
