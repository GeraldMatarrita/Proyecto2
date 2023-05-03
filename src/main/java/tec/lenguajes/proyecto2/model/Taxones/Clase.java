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

/*
 Clase que representa la clase de un taxon
*/
@Entity
@Getter
@Setter
public class Clase extends Taxon implements Serializable, Identificable {

    // Atributo que representa la division a la que pertenece la clase.
    @OneToOne
    private Division parent;

    // Lista de imagenes de la clase
    @OneToMany(mappedBy = "clase")
    private List<Image> imagesClase;
    public Clase() {
        super();
    }

    /*
     Método que verifica si la primera letra del nombre de la clase es mayúscula
     Parámetros:
        name: nombre de la clase
     Retorno: booleano que indica si la primera letra del nombre de la clase es mayúscula
     */
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    /*
     Método que verifica si el sufijo del nombre de la clase es "opsida"
     Parámetros:
        name: nombre de la clase
        Retorno: booleano que indica si el sufijo del nombre de la clase es "opsida"
     */
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>6? name.substring(name.length() - 6, name.length()): "";
        return suffix.equals("opsida");
    }
}
