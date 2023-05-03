package tec.lenguajes.proyecto2.model.Owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "person")
@PrimaryKeyJoinColumn(name = "owner_id")
//@NoArgsConstructor
public class Person extends Owner implements Serializable, Identificable {

    //Apellido de la persona
    private String lastName;

    // Constructor vacio
    public Person() {
        super();
    }

    // Imagenes de autoria de la persona
    @OneToMany(mappedBy = "author")
    private List<Image> images;

}
