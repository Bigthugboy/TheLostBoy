package codewiththugboy.clotth.controller;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;
import codewiththugboy.clotth.services.ClothService;
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
import java.util.stream.Stream;


@RestController
@RequestMapping("/api/v1/cloth")
@AllArgsConstructor
public class ClothController {
    private ClothService clothService;

    @PostMapping("/addCloth")
    public ResponseEntity<PostCloth> postCloth(@RequestBody PostRequest request) {
        PostCloth postCloth = clothService.post(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCloth);
    }

    @DeleteMapping("/deleteCloth")
    public ResponseEntity<DeleteCloth> deleteCloth(@RequestParam("clothId") Long clothId) {
        DeleteRequest request = new DeleteRequest();
        request.setId(clothId);
        DeleteCloth deleteCloth = clothService.delete(request);
        return ResponseEntity.ok(deleteCloth);
    }

    @PutMapping("/updateCloth/{clothId}")
    public ResponseEntity<UpdateCloth> updateCloth(@PathVariable("clothId") Long clothId, @RequestBody UpdateRequest request) {
        request.setClothId(clothId);
        UpdateCloth updateCloth = clothService.update(request);
        return ResponseEntity.ok(updateCloth);
    }

    @GetMapping("/cloths")
    public ResponseEntity<?> getAllBooks(
            @RequestParam(value = "pageNo", required = false) @DefaultValue({"0"}) @NonNull String pageNo,
            @RequestParam(value = "noOfItems", required = false) @DefaultValue({"10"}) @NonNull String noOfItems) {

        Map<String, Object> pageResult = clothService.getAllCloth(Integer.parseInt(pageNo), Integer.parseInt(noOfItems));

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/getCloth/{clothId}")
    public ResponseEntity<List<Cloth>> getCloth(@PathVariable("clothId") Long clothId) {
        List<Cloth> clothList = clothService.getClothById(clothId);
        return ResponseEntity.ok(clothList);
    }

    @GetMapping("/byDate")
    public ResponseEntity<Stream<Cloth>> getClothsByDateAdded(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Stream<Cloth> clothStream = clothService.getClothsByDateAdded(start, end);
        return ResponseEntity.ok(clothStream);
    }

    @GetMapping("/byDate/page")
    public ResponseEntity<Page<Cloth>> getAllClothByDateAdded(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Pageable pageable
    ) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Page<Cloth> clothPage = clothService.getAllClothByDateAdded(start, end, pageable);
        return ResponseEntity.ok(clothPage);
    }

    @GetMapping("/getClothByCollectionName")
    public ResponseEntity<?> getBookByCollectionName(@RequestParam @NonNull @NotBlank String collectionName) {
        Cloth cloth = clothService.findClothByCollectionName(collectionName);
        return new ResponseEntity<>(cloth, HttpStatus.FOUND);
    }

    @GetMapping("/getClothByDesignerName")
    public ResponseEntity<?> getBookByDesignerName(@RequestParam @NonNull @NotBlank String designerName) {
        Cloth cloth = clothService.findClothByDesignerName(designerName);
        return new ResponseEntity<>(cloth, HttpStatus.FOUND);
    }
    //TODO
    // refactor getCloth by price
    //Aws S3 and rdms, Ec2
    //notifications
    //testings
}