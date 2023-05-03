package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.util.List;
import java.util.Optional;
/*
    Repositorio de la clase Image.
    Hereda de JpaRepository.
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {

    // Método que busca una imagen por su id.
    Optional<Image> findFirstById(Integer id);

    // Método que cuenta las imágenes en la base de datos.
    @Query(value = "SELECT COUNT(i) FROM Image i")
    Integer countImages();

    // Método que busca una imagen por una palabra clave.
    List<Image> findByKeywordsContaining(String keyword);

}
