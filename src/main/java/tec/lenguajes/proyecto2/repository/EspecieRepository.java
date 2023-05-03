package tec.lenguajes.proyecto2.repository;

import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Taxones.Especie;
import tec.lenguajes.proyecto2.model.Taxones.Genero;

import java.util.Optional;
/*
    Repositorio de la clase Especie.
    Hereda de JpaRepository.
 */
public interface EspecieRepository extends JpaRepository<Especie, Integer> {

    // Método que busca una especie por su id.
    Optional<Especie> findFirstById(Integer id);

    // Método que busca una especie por su padre.
    Optional<Especie> findFirstByParent(Genero parent);
}