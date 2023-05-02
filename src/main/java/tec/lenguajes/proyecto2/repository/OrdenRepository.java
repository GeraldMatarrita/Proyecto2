package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Orden;

import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    Optional<Orden> findFirstById(Integer id);
}