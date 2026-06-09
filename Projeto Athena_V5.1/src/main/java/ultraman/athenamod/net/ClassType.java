package ultraman.athenamod.net;

public enum ClassType {
    NONE(0),
    WARRIOR(50),
    ROGUE(100),
    CLERIC(200),
    MAGE(250);

    private final int maxMana;

    ClassType(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean usesMana() {
        return maxMana > 0;
    }

    /** Converte String para ClassType de forma segura. */
    public static ClassType fromString(String name) {
        try {
            return ClassType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return NONE;
        }
    }
}
