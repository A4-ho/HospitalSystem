package src.enums;

public enum UserEnum {
    DOCTOR(2),
    PATIENT(1);

    private final int value;

    UserEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name() + " (" + value + ")";
    }
}
