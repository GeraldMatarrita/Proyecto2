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
    Clase que representa la familia de un taxon
 */
@Entity
@Getter
@Setter
public class Genero extends Taxon implements Serializable, Identificable {

    // Atributo que representa el orden al que pertenece la familia
    @OneToOne
    private Familia parent;

    // Lista de imagenes de la familia
    @OneToMany(mappedBy = "genero")
    private List<Image> imagesGenero;

    // Constructor vacio
    public Genero() {
        super();
    }

    /*
        Método que verifica si la primera letra del nombre de la familia es mayúscula.
        Parámetros:
            name: nombre de la familia
        Retorno: booleano que indica si la primera letra del nombre de la familia es mayúscula
     */
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    /*
        Método que solo retorna true porque no necesita verificación de sufijo
        Parámetros:
            name: nombre de la familia
        Retorno: true
     */
    @Override
    public boolean suffixVerification(String name) {
        return true;
    }
}
