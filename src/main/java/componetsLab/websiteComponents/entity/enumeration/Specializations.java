package componetsLab.websiteComponents.entity.enumeration;

public enum Specializations {
    DENTIST("dentist"),
    NEUROLOGIST("neurologist");

    private String description;

    Specializations(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
