package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Familia;

public interface FamiliaRepository extends JpaRepository<Familia, Integer> {
}