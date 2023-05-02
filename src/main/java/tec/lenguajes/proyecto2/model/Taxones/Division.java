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
public class Division extends Taxon implements Serializable {

    @OneToOne
    private Reino parent;

    @OneToMany(mappedBy = "division")
    private List<Image> imagesDivision;

    public Division() {
        super();
    }
}
