package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    Image findFirstById(Integer id);
}
