package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

/*
 Clase que representa la división de un taxon
*/
@Entity
@Getter
@Setter
public class Division extends Taxon implements Serializable, Identificable {

    // Atributo que representa el reino al que pertenece la división.
    @OneToOne
    private Reino parent;

    // Lista de imágenes de la división.
    @OneToMany(mappedBy = "division")
    private List<Image> imagesDivision;

    // Constructor vacio
    public Division() {
        super();
    }

    /*
     Método que verifica si la primera letra del nombre de la división es mayúscula
     Parámetros:
        name: nombre de la división
     Retorno: booleano que indica si la primera letra del nombre de la división es mayúscula
    */
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    /*
     Método que verifica si el sufijo del nombre de la división es "phyta"
     Parámetros:
        name: nombre de la división
     Retorno: booleano que indica si el sufijo del nombre de la división es "phyta"
     */
    @Override
    public boolean suffixVerification (String name) {
        String suffix = name.length()>5? name.substring(name.length() - 5, name.length()): "";
        return suffix.equals("phyta");
    }
}
