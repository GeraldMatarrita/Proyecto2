package tec.lenguajes.proyecto2.model.Taxones;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;

import java.util.Date;
/*
    Clase abstracta que representa a los taxones.
 */
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public abstract class Taxon implements Identificable {

    // Identificador del taxon.
    @Id
    private Integer id;

    // Nombre del taxon.
    private String scientific_name;

    // Autor del taxon.
    private String author;

    // Año de publicación del taxon
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publication_year;

    /*
     Método de verificación de la letra inicial del nombre científico.
     Parámetros:
        - name: nombre científico del taxón.
     Retorno:
        - true: si la letra inicial del nombre científico es correcta.
        - false: si la letra inicial del nombre científico es incorrecta.
     */
    public abstract boolean initialLetterVerification(String name);

    /*
      Método de verificación de la terminación del nombre científico.
      Parámetros:
         - name: nombre científico del taxón.
        Retorno:
            - true: si la terminación del nombre científico es correcta.
            - false: si la terminación del nombre científico es incorrecta.
     */
    public abstract boolean suffixVerification(String name);
}
