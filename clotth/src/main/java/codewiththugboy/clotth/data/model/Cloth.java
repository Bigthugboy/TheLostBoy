package codewiththugboy.clotth.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cloth {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CollectionName;
    @Enumerated(EnumType.STRING)
    private ClothSize clothSize;
    private double price;
    private String clothMaterial;
    private String clothType;
    private String clothStyle;
}
