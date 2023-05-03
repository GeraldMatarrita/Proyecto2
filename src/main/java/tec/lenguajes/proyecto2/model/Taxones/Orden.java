package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

/*
    Clase que representa el orden de un taxon
 */
@Entity
@Getter
@Setter
public class Orden extends Taxon implements Serializable, Identificable {

    // Atributo que representa la clase a la que pertenece la orden.
    @OneToOne
    private Clase parent;

    // Lista de imágenes de la orden
    @OneToMany(mappedBy = "orden")
    private List<Image> imagesOrden;

    // Método de colecta (Atributo propio de Orden). Se utiliza un Enum para limitar las posibles asignaciones.
    @Enumerated(EnumType.STRING)
    private CollectingMethod collecting_method;

    // Constructor vacío
    public Orden() {
        super();
    }

    /*
        Método que verifica si la primera letra del nombre de la orden es mayúscula
        Parámetros:
            name: nombre de la orden
        Retorno: booleano que indica si la primera letra del nombre de la orden es mayúscula
     */
    @Override
    public boolean initialLetterVerification(String name) {
        String capitalizeLetter = name.substring(0, 1).toUpperCase();
        return capitalizeLetter.equals(name.substring(0, 1));
    }

    /*
        Método que verifica si el sufijo del nombre de la orden es "ales"
        Parámetros:
            name: nombre de la orden
        Retorno: booleano que indica si el sufijo del nombre de la orden es "ales"
     */
    @Override
    public boolean suffixVerification(String name) {
        String suffix = name.length()>4? name.substring(name.length() - 4, name.length()): "";
        return suffix.equals("ales");
    }
}
