package src.enums;

public enum DoctorEnum {
    CARDIOLOGIST,
    DERMATOLOGIST,
    NEUROLOGIST,
    ORTHOPEDIC,
    PEDIATRICIAN,
    GENERAL_PRACTITIONER,
    RADIOLOGIST,
    SURGEON;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ");
    }
}
// privet