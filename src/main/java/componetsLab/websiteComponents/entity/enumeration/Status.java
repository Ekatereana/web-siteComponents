package componetsLab.websiteComponents.entity.enumeration;

public enum  Status {
    ACTIVE("active"),
    PROCESSED("processed"),
    PAID("paid");


    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
