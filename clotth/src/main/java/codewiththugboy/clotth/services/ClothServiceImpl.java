package codewiththugboy.clotth.services;


import codewiththugboy.clotth.data.model.Cloth;
import codewiththugboy.clotth.data.repo.ClothRepo;
import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;
import codewiththugboy.clotth.exceptions.RequestValidationException;
import codewiththugboy.clotth.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
public class ClothServiceImpl implements ClothService {
    private final ClothRepo clothRepo;


    @Override
    public PostCloth post(PostRequest request) {
        Cloth cloth;
        ModelMapper modelMapper = new ModelMapper();
        cloth = modelMapper.map(request, Cloth.class);
        cloth.setDateTime(LocalDate.now());
        cloth.setClothId(String.valueOf(UUID.randomUUID()).replace("-",""));
        var savedCloth = clothRepo.save(cloth);
        return PostCloth.builder()
                .clothId(savedCloth.getClothId())
                .datePosted(savedCloth.getDateTime())
                .CollectionName(savedCloth.getCollectionName())
                .build();
    }

    @Override
    public DeleteCloth delete(DeleteRequest request) throws ResourceNotFoundException {
        Optional<Cloth> cloth = clothRepo.findClothsByClothId(request.getClothId());
        cloth.ifPresent(clothRepo::delete);
        DeleteCloth deleteCloth = new DeleteCloth();
        deleteCloth.setMessage("cloth deleted successfully");
        deleteCloth.setDateDeleted(LocalDate.now());
        deleteCloth.setDeleted(true);
        return deleteCloth;
    }

    @Override
    public UpdateCloth update(UpdateRequest request) {
        try {
            Optional<Cloth> cloth = clothRepo.findClothsByClothId(request.getClothId());
            ModelMapper modelMapper = new ModelMapper();

            if (cloth.isPresent()) {
                Cloth updatedCloth = modelMapper.map(request, Cloth.class);
                clothRepo.save(updatedCloth);
                return UpdateCloth.builder()
                        .cloth(cloth.get())
                        .dateUpdated(LocalDate.now())
                        .clothId(updatedCloth.getClothId())
                        .build();
            }
        } catch (RequestValidationException e) {
            e.getCause();
        }

        throw new IllegalStateException("unable to perform action");
    }

    @Override
    public Map<String, Object> getAllCloth(int numberOfPages, int numberOfItems) {
        Pageable pageable = PageRequest.of(numberOfPages, numberOfItems, Sort.by("clothId"));
        Page<Cloth> page = clothRepo.findAll(pageable);
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("totalNumberOfPages", page.getTotalPages());
        pageResult.put("totalNumberOfElementsInDatabase", page.getTotalElements());
        if (page.hasNext()) {
            pageResult.put("nextPage", page.nextPageable());
        }
        if (page.hasPrevious()) {
            pageResult.put("previousPage", page.previousPageable());
        }
        pageResult.put("cloths", page.getContent());
        pageResult.put("NumberOfElementsInPage", page.getNumberOfElements());
        pageResult.put("pageNumber", page.getNumber());
        pageResult.put("size", page.getSize());
        return pageResult;
    }

    @Override
    public List<Cloth> getClothById(Long id) {
        Optional<Cloth> cloth = clothRepo.findById(id);
        if (cloth.isPresent()) {
            return cloth.stream().toList();
        }
        throw new ResourceNotFoundException("Cloth not found");
    }

    public Stream<Cloth> getClothsByDateAdded(LocalDate startDate, LocalDate endDate) {
        return clothRepo.findClothsByDateTimeBetween(startDate, endDate);
    }

    public Page<Cloth> getAllClothByDateAdded(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Cloth> cloth = clothRepo.findClothByDateTime(startDate, endDate, pageable);
        if (cloth.isEmpty()) {
            throw new ResourceNotFoundException("cloth not found");
        }

        return cloth;
    }

    @Override
    public List<Cloth> findClothByCollectionName(String collectionName) {
        Optional<Cloth> cloth = clothRepo.findClothsByCollectionName(collectionName);
        if (cloth.isPresent()) {
            return cloth.stream().toList();
        }
        throw new ResourceNotFoundException("Cloth not found");
    }

    @Override
    public List<Cloth> findClothByDesignerName(String designerName) {
        Optional<Cloth> cloth = clothRepo.findClothsByDesignerName(designerName);
        if (cloth.isPresent()) {
            return cloth.stream().toList();
        }
        throw new ResourceNotFoundException("Cloth not found");
    }

    @Override

        public List<Cloth> findClothByPriceRange(double minPrice, double maxPrice) throws ResourceNotFoundException {
            return clothRepo.findClothByPriceBetween(minPrice, maxPrice);
        }



    @Override
    public Optional<Cloth> findByClothId(String clothId) throws ResourceNotFoundException {
        return clothRepo.findClothsByClothId(clothId);
    }
}
