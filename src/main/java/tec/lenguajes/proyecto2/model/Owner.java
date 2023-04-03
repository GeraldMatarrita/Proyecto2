package tec.lenguajes.proyecto2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String country;
    private String phone;
    private String email;

    public Owner(Integer id, String name, String country, String phone, String email) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }
}
