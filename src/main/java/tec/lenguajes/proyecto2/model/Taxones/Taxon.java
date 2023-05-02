package tec.lenguajes.proyecto2.model.Taxones;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import tec.lenguajes.proyecto2.model.Image;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public abstract class Taxon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //nombre
    private String scientific_name;
    //autor
    private String author;
    //año de publicación del taxon
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publication_year;
}
