package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Clase;

import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Integer> {
    Optional<Clase> findFirstById(Integer id);
}