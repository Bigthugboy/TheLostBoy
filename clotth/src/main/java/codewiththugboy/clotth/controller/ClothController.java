package codewiththugboy.clotth.controller;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.dto.ApiResponse;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;

import codewiththugboy.clotth.services.ClothService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api/v1/cloth")
@AllArgsConstructor
public class ClothController {
    private ClothService clothService;

    @PostMapping("/addCloth")

    public ResponseEntity<?> postCloth(@RequestBody @Valid PostRequest request) {
            PostCloth postCloth = clothService.post(request);
            ApiResponse response = ApiResponse.builder()
                    .isSuccessfully(true)
                    .message("Cloth Save Successfully")
                    .Data(postCloth)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/deleteCloth")
    public ResponseEntity<?> deleteCloth(@RequestParam("clothId") String clothId) {
        DeleteRequest request = new DeleteRequest();
        request.setClothId(clothId);
        DeleteCloth deleteCloth = clothService.delete(request);
        ApiResponse response = ApiResponse.builder()
                .isSuccessfully(true)
                .message("Cloth Deleted")
                .Data(deleteCloth)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateCloth/{clothId}")
    public ResponseEntity<?> updateCloth(@PathVariable("clothId") String clothId, @RequestBody UpdateRequest request) {
        request.setClothId(clothId);
        UpdateCloth updateCloth = clothService.update(request);
        ApiResponse response = ApiResponse.builder()
                .isSuccessfully(true)
                .message("Cloth Updated Successfully")
                .Data(updateCloth)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cloths")
    public ResponseEntity<?> getAllBooks(
            @RequestParam(value = "pageNo", required = false) @DefaultValue({"0"}) @NonNull String pageNo,
            @RequestParam(value = "noOfItems", required = false) @DefaultValue({"10"}) @NonNull String noOfItems) {

        Map<String, Object> pageResult = clothService.getAllCloth(Integer.parseInt(pageNo), Integer.parseInt(noOfItems));
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("pages returned")
                .Data(pageResult)
                .result((int) pageResult.get("NumberOfElementsInPage"))
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getCloth/{clothId}")
    public ResponseEntity<?> getCloth(@PathVariable("clothId") Long clothId) {
        List<Cloth> clothList = clothService.getClothById(clothId);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(clothList)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> getClothsByDateAdded(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Stream<Cloth> clothStream = clothService.getClothsByDateAdded(start, end);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(clothStream)
                .result((int) clothStream.count())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/byDate/page")
    public ResponseEntity<?> getAllClothByDateAdded(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Pageable pageable
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Page<Cloth> clothPage = clothService.getAllClothByDateAdded(start, end, pageable);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(clothPage)
                .result(clothPage.getTotalPages())
                .build();
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/getClothByCollectionName")
    public ResponseEntity<?> getClothByCollectionName(@RequestParam @NonNull @NotBlank String collectionName) {
       List<Cloth> cloth = clothService.findClothByCollectionName(collectionName);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(cloth)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }

    @GetMapping("/getClothByDesignerName")
    public ResponseEntity<?> getClothByDesignerName(@RequestParam @NonNull @NotBlank String designerName) {
        List<Cloth> cloth = clothService.findClothByDesignerName(designerName);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(cloth)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }
    @GetMapping("/getClothByClothId")
    public ResponseEntity<?> getClothBtClothId(@RequestParam @NonNull @NotBlank String clothId) {
        Optional<Cloth> cloth = clothService.findByClothId(clothId);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(cloth)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }
    @GetMapping("/getClothByPrice")
    public ResponseEntity<?> getClothBtClothPrice(@RequestParam @NonNull @NotBlank String clothId) {
        Optional<Cloth> cloth = clothService.findByClothId(clothId);
        ApiResponse apiResponse = ApiResponse.builder()
                .isSuccessfully(true)
                .message("successfully")
                .Data(cloth)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
    }

        @GetMapping("/by-price-range")
        public List<Cloth> getClothesByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
            return clothService.findClothByPriceRange(minPrice, maxPrice);
        }


}
