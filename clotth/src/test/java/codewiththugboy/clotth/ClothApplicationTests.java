package codewiththugboy.clotth;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.data.model.ClothSize;
import codewiththugboy.clotth.data.repo.ClothRepo;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.services.ClothService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest


class ClothApplicationTests {
    @Autowired
    private ClothService clothService;


    @Mock
    private ClothRepo mockClothRepo;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Clean up any resources if needed
    }

    @Test
    void post_ShouldSaveClothAndReturnPostCloth_Success() {
        // Arrange
        PostRequest request = PostRequest.builder()
                .clothSize(ClothSize.SMALL)
                .clothMaterial("fabric")
                .clothStyle("beach")
                .clothType("free")
                .collectionName("ThugBoyCollection")
                .price(4000)
                .build();
        Cloth savedCloth = new Cloth();
        savedCloth.setId(1L);
        when(mockClothRepo.save(any(Cloth.class))).thenReturn(savedCloth);

        // Act
        PostCloth result = clothService.post(request);

        // Assert
        assertEquals(1, result.getClothId());
        assertEquals("ThugBoyCollection",result.getCollectionName());

        // Add additional assertions as needed to verify the correctness of the returned result
    }
}
