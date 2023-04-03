package tec.lenguajes.proyecto2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table
public class Person extends Owner implements Serializable {
    private String lastName;
    @OneToMany(mappedBy = "autor")
    private List<Image> images;


    public Person(Integer id, String name, String lastName, String country, String phone, String email) {
        super(id, name, country, phone, email);
        this.lastName = lastName;
    }
}
