package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String country;
    private String phone;
    private String email;

    private String type;

//    @OneToMany(mappedBy = "owner")
//    private List<Image> images;

    public Owner(String name, String country, String phone, String email) {
        this.name = name;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }
}
