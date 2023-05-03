package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Reino extends Taxon implements Serializable, Identificable {

    @OneToMany(mappedBy = "reino")
    private List<Image> imagesReino;

    public Reino() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification(String name) {
        return true;
    }
}
