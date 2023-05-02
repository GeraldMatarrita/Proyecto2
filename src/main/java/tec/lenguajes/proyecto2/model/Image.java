package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import tec.lenguajes.proyecto2.model.Taxones.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @ElementCollection
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "author")
    private Person author;
    @Enumerated(EnumType.STRING)
    private License license;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "owner_person", referencedColumnName = "id")
    @Where(clause = "owner_type = 'person'")
    private Person ownerPerson;

    @ManyToOne(targetEntity = Institution.class)
    @JoinColumn(name = "owner_institution", referencedColumnName = "id")
    private Institution ownerInstitution;

    @ManyToOne(targetEntity = Especie.class)
    @JoinColumn(name = "especie", referencedColumnName = "id")
    private Especie especie;

    @ManyToOne(targetEntity = Genero.class)
    @JoinColumn(name = "genero", referencedColumnName = "id")
    private Genero genero;

    @ManyToOne(targetEntity = Familia.class)
    @JoinColumn(name = "familia", referencedColumnName = "id")
    private Familia familia;

    @ManyToOne(targetEntity = Orden.class)
    @JoinColumn(name = "orden", referencedColumnName = "id")
    private Orden orden;

    @ManyToOne(targetEntity = Clase.class)
    @JoinColumn(name = "clase", referencedColumnName = "id")
    private Clase clase;

    @ManyToOne(targetEntity = Division.class)
    @JoinColumn(name = "division", referencedColumnName = "id")
    private Division division;

    @ManyToOne(targetEntity = Reino.class)
    @JoinColumn(name = "reino", referencedColumnName = "id")
    private Reino reino;

}
