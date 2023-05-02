package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
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
public class Clase extends Taxon implements Serializable {
    @OneToOne
    private Division parent;

    @OneToMany(mappedBy = "clase")
    private List<Image> imagesClase;
    public Clase() {
        super();
    }
}
