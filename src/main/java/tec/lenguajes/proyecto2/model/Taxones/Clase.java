package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
public class Clase extends Taxon implements Serializable, Identificable {
    @OneToOne
    private Division parent;

    @OneToMany(mappedBy = "clase")
    private List<Image> imagesClase;
    public Clase() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>6? name.substring(name.length() - 6, name.length()): "";
        return suffix.equals("opsida");
    }
}
