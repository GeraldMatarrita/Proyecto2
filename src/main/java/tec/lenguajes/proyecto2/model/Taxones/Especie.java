package tec.lenguajes.proyecto2.model.Taxones;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tec.lenguajes.proyecto2.model.Interfaces.Identificable;
import tec.lenguajes.proyecto2.model.Image.Image;

import java.io.Serializable;
import java.util.List;

/*
 Clase que representa la especie de un taxon
*/
@Entity
@Setter
@Getter
public class Especie extends Taxon implements Serializable, Identificable {

    // Atributo que representa el género al que pertenece la especie
    @OneToOne
    private Genero parent;

    // Ubicación de la especie en el estante
    private String specimen_location_rack;

    // Ubicación de la especie en el cajón
    private String specimen_location_drawer;

    // Lista de imagenes de la especie
    @OneToMany(mappedBy = "especie")
    private List<Image> imagesEspecie;

    // Constructor vacio
    public Especie() {
        super();
    }

    /*
      Método que verifica si la primera letra del nombre de la especie es minúscula
        Parámetros:
             name: nombre de la especie
        Retorno: booleano que indica si la primera letra del nombre de la especie es minúscula
    */
    @Override
    public boolean initialLetterVerification(String name) {
        String minusculeLetter = name.substring(0, 1).toLowerCase();
        return minusculeLetter.equals(name.substring(0, 1));
    }

    /*
     Método que solo retorna true porque no necesita verificación de sufijo
     Parámetros:
        name: nombre de la especie
     Retorno: true
     */
    @Override
    public boolean suffixVerification(String name) {
        return true;
    }
}
