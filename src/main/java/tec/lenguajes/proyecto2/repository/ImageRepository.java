package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findFirstById(Integer id);

    @Query(value = "SELECT COUNT(i) FROM Image i")
    Integer countImages();

    List<Image> findByKeywordsContaining(String keyword);

}
