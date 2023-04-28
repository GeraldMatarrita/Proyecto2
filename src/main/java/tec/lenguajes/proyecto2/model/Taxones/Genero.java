package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;

import java.io.Serializable;
import java.util.Date;

@Entity
//@NoArgsConstructor
public class Genero extends Taxon implements Serializable {

    public Genero() {
        super();
    }

    // Constructor con toda la información del taxon, heredado del padre.
    public Genero(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id) throws AncestorException, NameException {
        super(scientific_name, author, publication_year, taxon_ancestor_id);
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser mayúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return !capitalizeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso no hay requisito por lo que se retorna verdadero.
    @Override
    public boolean suffixVerification(String name) {
        return true;
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a mayúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(getScientific_name())) {
            setScientific_name(getScientific_name().substring(0, 1).toUpperCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Familia.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Familia".equals(ancestor.getClass().getName());
    }

}
