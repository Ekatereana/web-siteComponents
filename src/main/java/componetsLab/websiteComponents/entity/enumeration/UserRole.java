package componetsLab.websiteComponents.entity.enumeration;

public enum UserRole {
    DOCTOR("doctor"),
    PATIENT("patient"),
    ADMIN("admin");

    private String description;

    UserRole(String description) {
        this.description = description;
    }
}
