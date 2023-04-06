package tec.lenguajes.proyecto2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tec.lenguajes.proyecto2.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Owner findFirstById(Integer id);
}
