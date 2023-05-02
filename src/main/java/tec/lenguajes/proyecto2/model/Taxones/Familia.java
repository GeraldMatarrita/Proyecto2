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
@Setter
@Getter
public class Familia extends Taxon implements Serializable  {

    @OneToMany(mappedBy = "familia")
    private List<Image> imagesFamilia;

    public Familia() {
        super();
    }
}
