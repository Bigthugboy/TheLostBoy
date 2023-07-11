package codewiththugboy.clotth.data.repo;

import codewiththugboy.clotth.data.model.Cloth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.Optional;
import java.util.stream.Stream;


@Repository
public interface ClothRepo extends JpaRepository<Cloth,Long> {
     Stream<Cloth> findClothsByDateTimeBetween(LocalDate startDate, LocalDate endDate);
    Page<Cloth> findClothByDateTime(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Optional<Cloth>findClothsByCollectionName(String collectionName);
    Optional<Cloth>findClothsByDesignerName(String designerName);
}
