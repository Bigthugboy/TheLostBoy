package codewiththugboy.clotth.dto.request;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteRequest {
    private String clothId;
    private String CollectionName;


    public DeleteRequest(Long clothId) {
    }
}
