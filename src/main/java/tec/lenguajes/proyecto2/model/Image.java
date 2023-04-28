package tec.lenguajes.proyecto2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Taxones.Taxon;

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
    private Date date;
    @ElementCollection
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "author")
    private Person author;
    @Enumerated(EnumType.STRING)
    private License license;

    @ManyToOne
    @JoinColumn(name = "images")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "taxon")
    private Taxon taxon;

}
