package tec.lenguajes.proyecto2.model;

public enum License {
    CC_BY("CC_BY"),
    CC_BY_SA("CC_BY_SA"),
    CC_BY_NC("CC_BY_NC"),
    CC_BY_ND("CC_BY_ND");

    private final String value;

    License(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
