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
public class Division extends Taxon implements Serializable {

    public Division() {
        super();
    }

    // Constructor con toda la información del taxon, heredado del padre.
    public Division(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id) throws AncestorException, NameException {
        super(scientific_name, author, publication_year, taxon_ancestor_id);
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser mayúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return !capitalizeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso debe ser 'phyta'.
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>5? name.substring(name.length() - 5, name.length()): "";
        return suffix.equals("phyta");
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a mayúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(getScientific_name())){
            setScientific_name(getScientific_name().substring(0,1).toUpperCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Reino.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Reino".equals(ancestor.getClass().getName());
    }

}
