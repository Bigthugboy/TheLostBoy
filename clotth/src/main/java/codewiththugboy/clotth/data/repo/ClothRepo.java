package codewiththugboy.clotth.data.repo;

import codewiththugboy.clotth.data.model.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothRepo extends JpaRepository<Cloth,Long> {
}
