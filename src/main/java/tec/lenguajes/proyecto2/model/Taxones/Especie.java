package tec.lenguajes.proyecto2.model.Taxones;

import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;

import java.util.Date;

public class Especie extends Taxon{

    // ubicación del espécimen en los estantes de la colección.
    private String specimen_location_rack;
    private String specimen_location_drawer;

    // Constructor con toda la información del taxon, heredado del padre. Además, se asignan los atributos propios.
    public Especie(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id, String specimen_location_drawer, String specimen_location_rack) throws AncestorException, NameException {
        super(scientific_name, author, publication_year, taxon_ancestor_id);
        this.specimen_location_drawer = specimen_location_drawer;
        this.specimen_location_rack = specimen_location_rack;
    }

    // Gets y Sets de la ubicación del espécimen en los estantes de la colección.
    public String getSpecimen_location_rack() {
        return specimen_location_rack;
    }

    public void setSpecimen_location_rack(String specimen_location_rack) {
        this.specimen_location_rack = specimen_location_rack;
    }

    public String getSpecimen_location_drawer() {
        return specimen_location_drawer;
    }

    public void setSpecimen_location_drawer(String specimen_location_drawer) {
        this.specimen_location_drawer = specimen_location_drawer;
    }

    // Implementación de la verificación de la letra inicial. En este caso debe ser minúscula.
    @Override
    public boolean initialLetterVerification(String name) {
        String minusculeLetter = name.substring(0, 1).toLowerCase();
        return !minusculeLetter.equals(name.substring(0, 1));
    }

    // Implementación de la verificación del sufijo. En este caso no hay requisito por lo que se retorna verdadero.
    @Override
    public boolean suffixVerification(String name) {
        return true;
    }

    // Implementación de la actualización automática del nombre. En este caso se pasa a minúscula.
    @Override
    public void updateName() {
        if (initialLetterVerification(getScientific_name())){
            setScientific_name(getScientific_name().substring(0,1).toLowerCase() + getScientific_name().substring(1));
        }
    }

    // Implementación de la verificación del ancestro. En este caso debe ser Genero.
    @Override
    public Boolean ancestorVerification(Taxon ancestor) {
        return "Genero".equals(ancestor.getClass().getName());
    }
}
