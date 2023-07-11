package codewiththugboy.clotth.services;

import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ClothService {
    PostCloth post(PostRequest request);
    DeleteCloth delete(DeleteRequest request);
    UpdateCloth update (UpdateRequest request);
    Page<Cloth> getAllCloth(Pageable pageable);
    List<Cloth> getClothById(Long id);





}
