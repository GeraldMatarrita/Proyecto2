package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Orden;

import java.util.Optional;

/*
    Repositorio de la clase Orden.
    Hereda de JpaRepository.
 */
public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    // Método que busca una orden por su id.
    Optional<Orden> findFirstById(Integer id);
}