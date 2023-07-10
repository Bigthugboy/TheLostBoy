package codewiththugboy.clotth.controller;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;
import codewiththugboy.clotth.services.ClothService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<Page<Cloth>> getAllCloth(Pageable pageable) {
        Page<Cloth> clothPage = clothService.getAllCloth(pageable);
        return ResponseEntity.ok(clothPage);
    }
    @GetMapping("/getCloth/{clothId}")
    public ResponseEntity<List<Cloth>> getCloth(@PathVariable("clothId") Long clothId){
        List<Cloth> clothList = clothService.getClothById(clothId);
        return  ResponseEntity.ok(clothList);
    }
}
