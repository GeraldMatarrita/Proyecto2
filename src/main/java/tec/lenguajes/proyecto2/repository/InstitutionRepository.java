package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Institution findFirstBy(Integer id);
}
