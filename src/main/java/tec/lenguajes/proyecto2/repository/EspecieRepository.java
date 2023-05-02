package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Especie;

import java.util.Optional;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {

    Optional<Especie> findFirstById(Integer id);
}