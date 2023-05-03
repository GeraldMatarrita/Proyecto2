package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Reino;

import java.util.Optional;

/*
    Repositorio de la clase Reino.
    Hereda de JpaRepository.
 */
public interface ReinoRepository extends JpaRepository<Reino, Integer> {

    // Método que busca un reino por su id.
    Optional<Reino> findFirstById(Integer id);
}