package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Division;

import java.util.Optional;
/*
    Repositorio de la clase Division.
    Hereda de JpaRepository.
 */
public interface DivisionRepository extends JpaRepository<Division, Integer> {

    // Método que busca una division por su id.
    Optional<Division> findFirstById(Integer id);
}