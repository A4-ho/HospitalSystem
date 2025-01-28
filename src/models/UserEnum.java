package src.models;

public enum UserEnum {
    DOCTOR(2),
    PATIENT(1);

    private final int value;

    // Constructor to assign integer value to each enum constant
    UserEnum(int value) {
        this.value = value;
    }

    // Getter method to retrieve the integer value
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name() + " (" + value + ")";
    }
}
