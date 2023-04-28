package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;

import java.io.Serializable;
import java.util.Date;

@Entity
//@NoArgsConstructor
public class Orden extends Taxon implements Serializable {

    public Orden() {
        super();
    }

    // Método de colecta (Atributo propio de Orden). Se utiliza un Enum para limitar las posibles asignaciones.
    @Enumerated(EnumType.STRING)
    private CollectingMethod collecting_method;

    // Constructor con toda la información del taxon, heredado del padre. Además, se asigna el atributo propio.
    public Orden(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id, CollectingMethod collecting_method) throws AncestorException, NameException {
        super(scientific_name, author, publication_year, taxon_ancestor_id);
        this.collecting_method = collecting_method;
    }

    // Get y Set del método de colecta.
    public CollectingMethod getCollecting_method() {
        return collecting_method;
    }

    public void setCollecting_method(CollectingMethod collecting_method) {
        this.collecting_method = collecting_method;
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser mayúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return !capitalizeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso debe ser 'ales'.
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>4? name.substring(name.length() - 4, name.length()): "";
        return suffix.equals("ales");
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a mayúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(getScientific_name())) {
            setScientific_name(getScientific_name().substring(0, 1).toUpperCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Clase.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Clase".equals(ancestor.getClass().getName());
    }

}
