package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Institution;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Optional<Institution> findFirstById(Integer id);
}
