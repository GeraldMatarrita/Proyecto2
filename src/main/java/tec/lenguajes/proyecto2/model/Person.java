package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "person")
@PrimaryKeyJoinColumn(name = "owner_id")
//@NoArgsConstructor
public class Person extends Owner implements Serializable {

    private String lastName;

    public Person() {
        super();
    }

    @OneToMany(mappedBy = "author")
    private List<Image> images;


    public Person(Integer id, String name, String lastName, String country, String phone, String email) {
        super(name, country, phone, email);
        this.lastName = lastName;
    }

}
