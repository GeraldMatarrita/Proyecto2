package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.io.Serializable;

//@NoArgsConstructor

@Getter
@Setter
@Entity
@Table(name = "institution")
@PrimaryKeyJoinColumn(name = "owner_id")
public class Institution extends Owner implements Serializable {
    private String webSite;

    public Institution() {
        super();
    }

    public Institution(Integer id, String name, String country, String phone, String email, String webSite) {
        super(name, country, phone, email);
        this.webSite = webSite;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
