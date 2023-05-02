package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Reino;

import java.util.Optional;

public interface ReinoRepository extends JpaRepository<Reino, Integer> {
    Optional<Reino> findFirstById(Integer id);
}