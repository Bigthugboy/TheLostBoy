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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public interface ClothService {
    PostCloth post(PostRequest request);
    DeleteCloth delete(DeleteRequest request);
    UpdateCloth update (UpdateRequest request);
    Map<String, Object> getAllCloth(int numberOfPages, int numberOfItems);
    List<Cloth> getClothById(Long id);
    Stream<Cloth> getClothsByDateAdded(LocalDate startDate, LocalDate endDate);
    Page<Cloth> getAllClothByDateAdded(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Cloth findClothByCollectionName(String collectionName);
    Cloth findClothByDesignerName(String designerName);
    List<Cloth>getClothByPrice(double price);

    }