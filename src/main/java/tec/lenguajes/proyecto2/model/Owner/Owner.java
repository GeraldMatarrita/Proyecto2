package tec.lenguajes.proyecto2.model.Owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;

/*
 Clase abstracta que representa a los dueños de las imágenes.
*/

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Owner implements Identificable {

    // Identificador del dueño.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Nombre del dueño.
    private String name;

    // País del dueño.
    private String country;

    // Teléfono del dueño.
    private String phone;

    // Correo electrónico del dueño.
    private String email;

    // Tipo de dueño.
    private String type;

    public Owner(String name, String country, String phone, String email) {
        this.name = name;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }
}
