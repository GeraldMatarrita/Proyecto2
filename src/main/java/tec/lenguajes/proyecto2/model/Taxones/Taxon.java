package tec.lenguajes.proyecto2.model.Taxones;


import jakarta.persistence.*;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;
import tec.lenguajes.proyecto2.model.Image;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Taxon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //nombre
    private String scientific_name;
    //autor
    private String author;
    //año de publicación del taxon
    private Date publication_year;
    //ancestro
    @ManyToOne
    private Taxon taxon_ancestor;
    //Variable para asignar un id de forma automática

    @OneToMany(mappedBy = "taxon")
    private List<Image> images;

    // Constructor vacío
    public Taxon() {
    }

    // Constructor con toda la información del taxon.
    /*
        Antes de crear la instancia se verifica que el nombre tenga el sufijo correcto y que el ancestro que se quiere
        asignar es el correcto. En caso de que no se cumpla alguno de los dos requisitos se lanza una excepción.
        Además, si el nombre no tiene la letra inicial en minúscula o mayúscula (Según corresponda)
        se hace la corrección de forma automática.
     */
    public Taxon(String scientific_name, String author, Date publication_year, Taxon taxon_ancestor_id) throws AncestorException, NameException {
        if (suffixVerification(scientific_name)) {
            if (ancestorVerification(taxon_ancestor_id)) {
                this.taxon_ancestor = taxon_ancestor_id;
                this.scientific_name = scientific_name;
                updateName();
                this.author = author;
                this.publication_year = publication_year;
            } else {
                throw new AncestorException("El ancestro " + taxon_ancestor_id.getClass().getName() + " no es válido para " + this.getClass().getName());
            }
        } else {
            throw new NameException("El nombre, " + scientific_name + ", no tiene el formato correcto para " + this.getClass().getName());
        }
    }

    // Constructor para los taxones que no tengan un ancestro.
    /*
        No debe verificar ni el sufijo ni el ancestro por lo que no lanza ninguna excepción, solamente se hace la actualización
        de la letra inicial en caso de ser necesario.
     */
    public Taxon(String scientific_name, String author, Date publication_year) {
        this.scientific_name = scientific_name;
        updateName();
        this.author = author;
        this.publication_year = publication_year;
    }

    // Get y Set del nombre
    public String getScientific_name() {
        return scientific_name;
    }

    /*
        Antes de modificar la instancia se verifica que el nombre tenga el sufijo correcto. En este caso no es necesario
        lanzar una excepción dado que solamente no se modifica la instancia que ya existe.
        Además, si el nombre no tiene la letra inicial en minúscula o mayúscula (Según corresponda) se hace la
        corrección de forma automática.
     */
    public void setScientific_name(String scientific_name) {
        if (suffixVerification(scientific_name)) {
            this.scientific_name = scientific_name;
            updateName();
        } else {
            System.err.println("El nombre, " + scientific_name + ", no tiene el formato correcto para " + this.getClass().getName());
        }
    }

    // Get y Set del autor.
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Get y Set del año de publicación del taxon.
    public Date getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(Date publication_year) {
        this.publication_year = publication_year;
    }

    // Get y Set del ancestro.
    public Taxon getTaxon_ancestor() {
        return taxon_ancestor;
    }
    /*
        Antes de modificar la instancia se verifica que el ancestro que se quiere asignar es el correcto. En este caso
        no es necesario lanzar una excepción dado que solamente no se modifica la instancia que ya existe.
    */
    public void setTaxon_ancestor(Taxon taxon_ancestor) {
        if (ancestorVerification(taxon_ancestor)) {
            this.taxon_ancestor = taxon_ancestor;
        } else {
            System.err.println("El ancestro " + taxon_ancestor.getClass().getName() + " no es válido para " + this.getClass().getName());
        }
    }

    // Método abstracto para verificar si el nombre tiene la letra inicial en mayúscula o minúscula según corresponda.
    public abstract boolean initialLetterVerification(String name);

    // Método abstracto para verificar que el sufijo del nombre es el que corresponde.
    public abstract boolean suffixVerification(String name);

    // Método abstracto que actualiza el nombre de la instancia para que cumpla el formato de la letra inicial.
    public abstract void updateName();

    // Método abstracto para verificar que el ancestro es del nivel que corresponde.
    public abstract Boolean ancestorVerification(Taxon ancestor);

    // Método toString que muestra el taxon de la forma Nivel: nombre
    @Override
    public String toString() {
        return getClass().getName() + ": " + scientific_name;
    }

}
