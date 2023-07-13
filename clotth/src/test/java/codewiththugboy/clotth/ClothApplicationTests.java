package codewiththugboy.clotth;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.data.model.ClothSize;
import codewiththugboy.clotth.data.repo.ClothRepo;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;
import codewiththugboy.clotth.exceptions.RequestValidationException;
import codewiththugboy.clotth.exceptions.ResourceNotFoundException;
import codewiththugboy.clotth.services.ClothService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

@Slf4j
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
                .designerName("deji")
                .clothType("free")
                .collectionName("ThugBoyCollection")
                .price(4000)
                .build();
        Cloth savedCloth = new Cloth();
        savedCloth.setClothId(1L);
        LocalDate dateTime = LocalDate.now();
        savedCloth.setDateTime(dateTime);
        when(mockClothRepo.save(any(Cloth.class))).thenReturn(savedCloth);

        // Act
        PostCloth result = clothService.post(request);

        // Assert
        assertEquals(1, result.getClothId());

        assertEquals("ThugBoyCollection",result.getCollectionName());

        // Add additional assertions as needed to verify the correctness of the returned result
    }
    @Test
    void delete_ShouldDeleteExistingCloth_Success() {
        try{
            DeleteRequest request = DeleteRequest.builder()
                    .id(1L)
                    .CollectionName("ThugBoyCollection")
                    .build();
            // Arrange
            Cloth cloth = new Cloth();
            when(mockClothRepo.findById(any())).thenReturn(Optional.of(cloth));

            // Act
            DeleteCloth result = clothService.delete(request);

            // Assert
            assertNotNull(result);
            assertEquals("cloth deleted",result.getMessage());
            assertTrue(true);
            verify(mockClothRepo, times(1)).delete(cloth);
        }catch (ResourceNotFoundException e)
        {
                    e.getMessage();
        }

    }
    @Test
    void update_ShouldUpdateExistingCloth_Success() {
        // Arrange
       UpdateRequest request = UpdateRequest.builder()
               .clothId(1L)
               .clothSize(ClothSize.SMALL)
               .clothMaterial("leather")
               .clothStyle("party")
               .clothType("free")
               .collectionName("ThugBoyCollection")
               .price(4000)
               .build();


        Cloth savedCloth = new Cloth();
        savedCloth.setClothId(1L);
        when(mockClothRepo.findById(any())).thenReturn(Optional.of(savedCloth));

        // Act
        System.out.println(savedCloth);
        UpdateCloth result = clothService.update(request);
        System.out.println(result);


        assertEquals(1L, result.getId());
        assertEquals("leather", savedCloth.getClothMaterial());
        assertEquals("party", savedCloth.getClothStyle());

    }

    @Test
    void update_ShouldThrowRequestValidationException_WhenClothNotFound() {
        // Arrange
        long clothId = 1L;
        UpdateRequest request = new UpdateRequest();
        when(mockClothRepo.findById(clothId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RequestValidationException.class, () -> clothService.update(request));
        verify(mockClothRepo, never()).save(any());
    }
//    @Test
//    public void testGetAllCloth() {
//        // Create a list of Cloth objects for testing
//        List<Cloth> clothList = new ArrayList<>();
//        clothList.add(new Cloth(1L, "\"ThugBoyCollection\"",ClothSize.SMALL,4000,"party","free","big"));
//        clothList.add(new Cloth(2L, "\"ThugBoyCollection\"",ClothSize.EXTRA_EXTRA_LARGE,4300,"casual","free","free"));
//        clothList.add(new Cloth(3L, "\"ThugBoyCollection\"",ClothSize.EXTRA_LARGE,800,"smart","body fitted","big"));
//
//        // Create a mock Page object with the clothList
//        Page<Cloth> clothPage = new PageImpl<>(clothList);
//
//        // Mock the findAll() method of the clothRepo
//        when(mockClothRepo.findAll(Pageable.unpaged())).thenReturn(clothPage);
//
//        // Call the getAllCloth() method
//        Page<Cloth> result = clothService.getAllCloth(Pageable.unpaged());
//
//        // Assert that the returned Page matches the mock Page
//        assertEquals(clothPage, result);
//    }
}








