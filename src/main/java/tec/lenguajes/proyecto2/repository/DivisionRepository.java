package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Division;

import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    Optional<Division> findFirstById(Integer id);
}