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
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClothServiceImpl implements ClothService {
    private final ClothRepo clothRepo;


    @Override
    public PostCloth post(PostRequest request) {
        Cloth cloth = new Cloth();
        ModelMapper modelMapper = new ModelMapper();
        cloth = modelMapper.map(request, Cloth.class);
        var savedCloth = clothRepo.save(cloth);
        return PostCloth.builder()
                .clothId(savedCloth.getId())
                .CollectionName(savedCloth.getCollectionName())
                .build();
    }

    @Override
    public DeleteCloth delete(DeleteRequest request) {
        Optional<Cloth> cloth = clothRepo.findById(request.getId());
        cloth.ifPresent(clothRepo::delete);
        throw new ResourceNotFoundException("clothId not found ");
    }

    @Override
    public UpdateCloth update(UpdateRequest request) {
        Optional<Cloth> cloth = clothRepo.findById(request.getClothId());
        ModelMapper modelMapper = new ModelMapper();

        if (cloth.isPresent()) {
            Cloth updatedCloth = modelMapper.map(request, Cloth.class);
            clothRepo.save(updatedCloth);
            return UpdateCloth.builder()
                    .cloth(cloth.get())
                    .id(updatedCloth.getId())

                    .build();
        } else {
            throw new RequestValidationException("Unable to save entity");
        }

    }
}
