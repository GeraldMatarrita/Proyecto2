package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Owner.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findFirstById (Integer id);
}
