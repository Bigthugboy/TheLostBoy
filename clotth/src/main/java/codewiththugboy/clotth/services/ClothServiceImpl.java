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
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClothServiceImpl implements ClothService {
    private final ClothRepo clothRepo;


    @Override
    public PostCloth post(PostRequest request) {
        Cloth cloth ;
        ModelMapper modelMapper = new ModelMapper();
        cloth = modelMapper.map(request, Cloth.class);

        var savedCloth = clothRepo.save(cloth);
        return PostCloth.builder()
                .clothId(savedCloth.getId())
                .CollectionName(savedCloth.getCollectionName())
//                .datePosted(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy, hh:mm a").format(cloth.getDateTime()))
                .build();
    }

    @Override
    public DeleteCloth delete(DeleteRequest request) {
        Optional<Cloth> cloth = clothRepo.findById(request.getId());
        cloth.ifPresent(clothRepo::delete);
        DeleteCloth deleteCloth = new DeleteCloth();
//        deleteCloth.setDateDeleted(cloth.get().getDateTime());
        deleteCloth.setDeleted(true);
        throw new ResourceNotFoundException("clothId not found ");
    }

    @Override
    public UpdateCloth update(UpdateRequest request) {
        try{
            Optional<Cloth> cloth = clothRepo.findById(request.getClothId());
            ModelMapper modelMapper = new ModelMapper();

            if (cloth.isPresent()) {
                Cloth updatedCloth = modelMapper.map(request, Cloth.class);
                clothRepo.save(updatedCloth);
                return UpdateCloth.builder()
                        .cloth(cloth.get())
                        .id(updatedCloth.getId())
                        .build();
            }
        }catch (RequestValidationException e) {
            e.getCause();
        }

        return null;
    }

    @Override
    public Page<Cloth> getAllCloth(Pageable pageable) {
        pageable.getPageNumber();
        return clothRepo.findAll(pageable);
    }

    @Override
    public List<Cloth> getClothById(Long id) {
        Optional<Cloth> cloth = clothRepo.findById(id);
        if (cloth.isPresent()){
            return cloth.stream().toList();
        }
       throw new ResourceNotFoundException("Cloth not found");
    }
}
