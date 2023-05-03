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
public class Orden extends Taxon implements Serializable, Identificable {

    @OneToOne
    private Clase parent;

    @OneToMany(mappedBy = "orden")
    private List<Image> imagesOrden;

    // Método de colecta (Atributo propio de Orden). Se utiliza un Enum para limitar las posibles asignaciones.
    @Enumerated(EnumType.STRING)
    private CollectingMethod collecting_method;

    public Orden() {
        super();
    }

    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>4? name.substring(name.length() - 4, name.length()): "";
        return suffix.equals("ales");
    }
}
