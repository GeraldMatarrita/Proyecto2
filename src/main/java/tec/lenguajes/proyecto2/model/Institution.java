package tec.lenguajes.proyecto2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Institution extends Owner{

    @Id
    private Integer id;
    private String webSite;

    public Institution(Integer id, String name, String country, String phone, String email, String webSite) {
        super(id, name, country, phone, email);
        this.webSite = webSite;
    }

}
