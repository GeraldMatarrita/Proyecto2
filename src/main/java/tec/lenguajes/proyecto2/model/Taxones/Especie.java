package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;
import tec.lenguajes.proyecto2.model.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
public class Especie extends Taxon implements Serializable {

    @OneToOne
    private Genero parent;

    private String specimen_location_rack;
    private String specimen_location_drawer;

    @OneToMany(mappedBy = "especie")
    private List<Image> imagesEspecie;
    public Especie() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String minusculeLetter = name.substring(0, 1).toLowerCase();
        return minusculeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification(String name) {
        return true;
    }
}
