package tec.lenguajes.proyecto2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

//@NoArgsConstructor
@Getter
@Setter
@Entity
public class Institution extends Owner implements Serializable {
    private String webSite;

    public Institution() {
        super();
    }

    public Institution(Integer id, String name, String country, String phone, String email, String webSite) {
        super(name, country, phone, email);
        this.webSite = webSite;
    }
}
