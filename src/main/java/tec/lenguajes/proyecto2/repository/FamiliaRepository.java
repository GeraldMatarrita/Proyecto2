package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Familia;

import java.util.Optional;

public interface FamiliaRepository extends JpaRepository<Familia, Integer> {
    Optional<Familia> findFirstById(Integer id);
}