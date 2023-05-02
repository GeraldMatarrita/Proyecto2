package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Image;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Genero extends Taxon implements Serializable {

    @OneToMany(mappedBy = "genero")
    private List<Image> imagesGenero;

    public Genero() {
        super();
    }
}
