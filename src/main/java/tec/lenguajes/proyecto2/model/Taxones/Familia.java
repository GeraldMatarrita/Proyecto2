package tec.lenguajes.proyecto2.model.Taxones;

import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;

import java.util.Date;

public class Familia extends Taxon {

    // Constructor con toda la información del taxon, heredado del padre.
    public Familia(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id) throws AncestorException, NameException {
        super(scientific_name, author, publication_year, taxon_ancestor_id);
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser mayúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return !capitalizeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso debe ser 'eae'.
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>3? name.substring(name.length() - 3, name.length()): "";
        return suffix.equals("eae");
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a mayúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(getScientific_name())){
            setScientific_name(getScientific_name().substring(0,1).toUpperCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Orden.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Orden".equals(ancestor.getClass().getName());
    }

}
