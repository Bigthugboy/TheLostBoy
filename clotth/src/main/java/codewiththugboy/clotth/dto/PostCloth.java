package codewiththugboy.clotth.dto;

import codewiththugboy.clotth.data.model.Cloth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostCloth {
    private long clothId;
    private String CollectionName;

}
