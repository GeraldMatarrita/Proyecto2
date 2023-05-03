package tec.lenguajes.proyecto2.model.Image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Owner.Institution;
import tec.lenguajes.proyecto2.model.Owner.Person;
import tec.lenguajes.proyecto2.model.Taxones.*;
import tec.lenguajes.proyecto2.repository.ImageRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Esta clase representa una imagen que puede ser propiedad de una persona o institución.
 * Cada imagen puede tener un autor, una descripción, una fecha y una lista de palabras clave.
 * Además, puede ser asociada con una especie, un género, una familia, una orden, una clase, una división y un reino.
 * Implementa la interfaz Serializable y la interfaz Identificable para poder ser serializada y para tener un identificador único.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image implements Serializable, Identificable {

    // Identificador único de la imagen.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Descripción de la imagen.
    private String description;

    // Fecha de la imagen.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    // Lista de palabras clave de la imagen.
    @ElementCollection
    private List<String> keywords;

    // Contador de imágenes.
    @Transient
    private static int count;

    // Autor de la imagen.
    @ManyToOne
    @JoinColumn(name = "author")
    private Person author;

    // Licencia de la imagen.
    @Enumerated(EnumType.STRING)
    private License license;

    // Ruta de la imagen.
    private String path;

    // Dueño de la imagen (En caso de que sea persona).
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "owner_person", referencedColumnName = "id")
    @Where(clause = "owner_type = 'person'")
    private Person ownerPerson;

    // Dueño de la imagen (En caso de que sea institución).
    @ManyToOne(targetEntity = Institution.class)
    @JoinColumn(name = "owner_institution", referencedColumnName = "id")
    private Institution ownerInstitution;

    // Especie asociada a la imagen.
    @ManyToOne(targetEntity = Especie.class)
    @JoinColumn(name = "especie", referencedColumnName = "id")
    private Especie especie;

    // Género asociado a la imagen.
    @ManyToOne(targetEntity = Genero.class)
    @JoinColumn(name = "genero", referencedColumnName = "id")
    private Genero genero;

    // Familia asociada a la imagen.
    @ManyToOne(targetEntity = Familia.class)
    @JoinColumn(name = "familia", referencedColumnName = "id")
    private Familia familia;

    // Orden asociada a la imagen.
    @ManyToOne(targetEntity = Orden.class)
    @JoinColumn(name = "orden", referencedColumnName = "id")
    private Orden orden;

    // Clase asociada a la imagen.
    @ManyToOne(targetEntity = Clase.class)
    @JoinColumn(name = "clase", referencedColumnName = "id")
    private Clase clase;

    // División asociada a la imagen.
    @ManyToOne(targetEntity = Division.class)
    @JoinColumn(name = "division", referencedColumnName = "id")
    private Division division;

    // Reino asociado a la imagen.
    @ManyToOne(targetEntity = Reino.class)
    @JoinColumn(name = "reino", referencedColumnName = "id")
    private Reino reino;

    /*
     Método para eliminar los artículos de una lista de palabras.
     Parámetros:
         - palabras: Lista de palabras.
     Retorno: Lista de palabras sin artículos.
    */
    public static List<String> eliminarArticulos(List<String> palabras) {
        List<String> palabrasSinArticulos = new ArrayList<>();

        // Artículos en español
        List<String> articulos = Arrays.asList("el", "la", "los", "las", "un", "una", "unos", "unas");

        for (String palabra : palabras) {
            if (!articulos.contains(palabra.toLowerCase())) {
                palabrasSinArticulos.add(palabra);
            }
        }

        return palabrasSinArticulos;
    }

    /*
       Método para contar el número de imágenes en la base de datos.
         Parámetros:
             - imageRepository: Repositorio de imágenes.
         Retorno: Número de imágenes en la base de datos.
     */
    public static Integer updateCount(ImageRepository imageRepository) {
        return imageRepository.countImages();
    }

    public List<String> getKeywords() {
        return keywords;
    }

    /*
       Método para actualizar las palabras clave de la imagen.
       Parámetros: Ninguno.
       Retorno: Ninguno.
     */
    public void updateKeywords() {
        List<String> keywords = new ArrayList<>();
        this.keywords.add(this.getAuthor().getName());
        if (this.getOwnerPerson() != null) {
            this.keywords.add(this.getOwnerPerson().getLastName());
        } else {
            this.keywords.add(this.getOwnerInstitution().getWebSite());
        }
        this.keywords.add(this.getAuthor().getName());
        this.keywords.add(this.getAuthor().getLastName());
        if (this.getEspecie() != null) {
            this.keywords.add(this.getEspecie().getScientific_name());
        }
        if (this.getGenero() != null) {
            this.keywords.add(this.getGenero().getScientific_name());
        }
        if (this.getFamilia() != null) {
            this.keywords.add(this.getFamilia().getScientific_name());
        }
        if (this.getOrden() != null) {
            this.keywords.add(this.getOrden().getScientific_name());
        }
        if (this.getClase() != null) {
            this.keywords.add(this.getClase().getScientific_name());
        }
        if (this.getDivision() != null) {
            this.keywords.add(this.getDivision().getScientific_name());
        }
        if (this.getReino() != null) {
            this.keywords.add(this.getReino().getScientific_name());
        }
        List<String> description = Arrays.stream(this.getDescription().split(" ")).toList();
        Image.eliminarArticulos(description);
        this.keywords.addAll(description);
    }

}
