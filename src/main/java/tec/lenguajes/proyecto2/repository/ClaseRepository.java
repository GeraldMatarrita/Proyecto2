package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Clase;
import tec.lenguajes.proyecto2.model.Taxones.Division;

import java.util.Optional;
/*
    Repositorio de la clase Clase.
    Hereda de JpaRepository.
 */
public interface ClaseRepository extends JpaRepository<Clase, Integer> {

    // Método que busca una clase por su id.
    Optional<Clase> findFirstById(Integer id);

    // Método que busca una clase por su padre.
    Optional<Clase> findFirstByParent(Division parent);
}