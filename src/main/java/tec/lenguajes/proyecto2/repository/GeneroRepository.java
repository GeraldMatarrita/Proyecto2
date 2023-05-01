package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
}