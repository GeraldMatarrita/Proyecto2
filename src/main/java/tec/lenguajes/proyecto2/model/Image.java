package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import tec.lenguajes.proyecto2.model.Taxones.Especie;
import tec.lenguajes.proyecto2.model.Taxones.Taxon;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
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

    @ManyToOne
    private Especie especie;

}
