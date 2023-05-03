package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Division extends Taxon implements Serializable, Identificable {

    @OneToOne
    private Reino parent;

    @OneToMany(mappedBy = "division")
    private List<Image> imagesDivision;

    public Division() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification (String name) {
        String suffix = name.length()>5? name.substring(name.length() - 5, name.length()): "";
        return suffix.equals("phyta");
    }
}
