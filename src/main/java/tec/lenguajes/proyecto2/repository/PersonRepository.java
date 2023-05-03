package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Owner.Person;

/*
    Repositorio de la clase Person.
    Hereda de JpaRepository.
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // Método que busca una persona por su id.
    Person findFirstById (Integer id);
}
