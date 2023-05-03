package tec.lenguajes.proyecto2.model.Owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;

import java.io.Serializable;



@Getter
@Setter
@Entity
@Table(name = "institution")
@PrimaryKeyJoinColumn(name = "owner_id")
public class Institution extends Owner implements Serializable, Identificable {

    // Sitio web de la institucion
    private String webSite;

    // Constructor vacio
    public Institution() {
        super();
    }

    // Getters y setters
    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
