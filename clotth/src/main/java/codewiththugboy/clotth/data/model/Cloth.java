package codewiththugboy.clotth.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ClothId;
    private String collectionName;
    private String designerName;

    @Enumerated(EnumType.STRING)
    private ClothSize clothSize;

    private double price;
    private String clothMaterial;
    private String clothType;
    private String clothStyle;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateTime;

    private String clothImage;


    public Cloth(String collectionName, String designerName, ClothSize clothSize, double price, String clothMaterial, String clothType, String clothStyle, LocalDate dateTime, String clothImage) {
        this.collectionName = collectionName;
        this.designerName = designerName;
        this.clothSize = clothSize;
        this.price = price;
        this.clothMaterial = clothMaterial;
        this.clothType = clothType;
        this.clothStyle = clothStyle;
        this.dateTime = dateTime;
        this.clothImage = clothImage;
    }
}








