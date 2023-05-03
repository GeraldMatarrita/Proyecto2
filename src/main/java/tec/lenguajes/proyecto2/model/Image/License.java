package tec.lenguajes.proyecto2.model.Image;

/*
 Enumeración de las licencias que se pueden utilizar para las imágenes
*/
public enum License {

    // Licencias.
    CC_BY("CC_BY"),
    CC_BY_SA("CC_BY_SA"),
    CC_BY_NC("CC_BY_NC"),
    CC_BY_ND("CC_BY_ND");

    // Valor de la licencia.
    private final String value;

    // Constructor de la enumeración.
    License(String value) {
        this.value = value;
    }

    // Getter del valor de la licencia.
    public String getValue() {
        return value;
    }
}
