package componetsLab.websiteComponents.entity.enumeration;

public enum Specializations {
    DENTIST("dentist"),
    NEUROLOGIST("neurologist"),
    SURGEON("surgeon"),
    UROLOGIST("urologist"),
    PSYCHIATRIST("psychiatrist"),
    OTOLATYNGOLOGIST("otolaryngologist"),
    OBSTETRICIAN("obstetrician"),
    DERMATOLOGIST("dermatologist"),
    CARDIOLOGIST("cardiologist"),
    GYNECOLOGISR4("gynecologist");

    private String description;

    Specializations(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
