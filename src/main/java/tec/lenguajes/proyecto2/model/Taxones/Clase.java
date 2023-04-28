package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ValueGenerationType;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;

import java.io.Serializable;
import java.util.Date;

@Entity
//@NoArgsConstructor
public class Clase extends Taxon implements Serializable {

    public Clase() {
        super();
    }

    // Constructor con toda la información del taxon, heredado del padre.
    public Clase(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id) throws AncestorException, NameException{
        super(scientific_name, author, publication_year, taxon_ancestor_id);
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser mayúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return !capitalizeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso debe ser 'opsida'.
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>6? name.substring(name.length() - 6, name.length()): "";
        return suffix.equals("opsida");
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a mayúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(this.getScientific_name())){
            setScientific_name(getScientific_name().substring(0,1).toUpperCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Division.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Division".equals(ancestor.getClass().getName());
    }

}
