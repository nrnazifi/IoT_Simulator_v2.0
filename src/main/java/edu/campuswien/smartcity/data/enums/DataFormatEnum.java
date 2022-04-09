package edu.campuswien.smartcity.data.enums;

public enum DataFormatEnum {
    JSON(0, "JSON"),
    CSV(1, "CSV"),
    XML(2, "XML");

    private int id;
    private String description;

    DataFormatEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
