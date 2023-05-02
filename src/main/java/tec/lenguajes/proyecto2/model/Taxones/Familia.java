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

    @OneToOne
    private Orden parent;

    @OneToMany(mappedBy = "familia")
    private List<Image> imagesFamilia;

    public Familia() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>3? name.substring(name.length() - 3, name.length()): "";
        return suffix.equals("eae");
    }
}
