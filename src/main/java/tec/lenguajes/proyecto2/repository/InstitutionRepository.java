package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Owner.Institution;

import java.util.Optional;

/*
    Repositorio de la clase Institution.
    Hereda de JpaRepository.
 */
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {


    // Método que busca una institucion por su id.
    Optional<Institution> findFirstById(Integer id);
}
