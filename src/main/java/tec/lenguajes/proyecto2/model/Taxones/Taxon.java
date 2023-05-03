package tec.lenguajes.proyecto2.model.Taxones;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public abstract class Taxon implements Identificable {
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

    public abstract boolean initialLetterVerification(String name);

    public abstract boolean suffixVerification(String name);
}
