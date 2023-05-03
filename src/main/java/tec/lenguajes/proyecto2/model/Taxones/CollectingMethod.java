package tec.lenguajes.proyecto2.model.Taxones;

/*
Enumeración de los métodos de recolección de las plantas.
*/
public enum CollectingMethod {

    // Método de recolección de la planta.
    MANUAL("Manual"),
    CLIPPERS("Tijeras podadoras de mano"),
    SHEARS_WHIT_EXTENSION("Tijeras cortarramas con extensión");

    // Texto del método de recolección.
    public final String text;

    // Constructor de la enumeración.
    CollectingMethod(String text) {
        this.text = text;
    }

    // Getter del texto del método de recolección.
    public String getText() {
        return text;
    }

}
