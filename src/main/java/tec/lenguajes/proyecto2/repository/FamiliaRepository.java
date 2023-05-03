package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Familia;

import java.util.Optional;
/*
    Repositorio de la clase Familia.
    Hereda de JpaRepository.
 */
public interface FamiliaRepository extends JpaRepository<Familia, Integer> {
    // Método que busca una familia por su id.
    Optional<Familia> findFirstById(Integer id);
}