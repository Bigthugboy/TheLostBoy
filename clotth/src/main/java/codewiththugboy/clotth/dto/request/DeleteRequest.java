package codewiththugboy.clotth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteRequest {
    private Long id;
    private String CollectionName;
}
