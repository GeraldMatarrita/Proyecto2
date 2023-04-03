package tec.lenguajes.proyecto2.model.Taxones;

public enum CollectingMethod {

    MANUAL("Manual"),
    CLIPPERS("Tijeras podadoras de mano"),
    SHEARS_WHIT_EXTENSION("Tijeras cortarramas con extensión");

    public final String text;

    CollectingMethod(String text) {
        this.text = text;
    }
}
