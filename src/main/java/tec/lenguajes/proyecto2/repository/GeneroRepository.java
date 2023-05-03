package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Familia;
import tec.lenguajes.proyecto2.model.Taxones.Genero;

import java.util.Optional;
/*
    Repositorio de la clase Genero.
    Hereda de JpaRepository.
 */
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

    // Método que busca un genero por su id.
    Optional<Genero> findFirstById(Integer id);

    // Método que busca un genero por su padre.
    Optional<Genero> findFirstByParent(Familia parent);
}