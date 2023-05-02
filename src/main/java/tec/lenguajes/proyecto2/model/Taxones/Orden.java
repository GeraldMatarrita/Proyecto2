package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tec.lenguajes.proyecto2.Exception.AncestorException;
import tec.lenguajes.proyecto2.Exception.NameException;
import tec.lenguajes.proyecto2.model.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Orden extends Taxon implements Serializable {

    @OneToOne
    private Clase parent;

    @OneToMany(mappedBy = "orden")
    private List<Image> imagesOrden;

    // Método de colecta (Atributo propio de Orden). Se utiliza un Enum para limitar las posibles asignaciones.
    @Enumerated(EnumType.STRING)
    private CollectingMethod collecting_method;

    public Orden() {
        super();
    }
}
