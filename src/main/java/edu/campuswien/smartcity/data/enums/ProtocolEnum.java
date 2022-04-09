package edu.campuswien.smartcity.data.enums;

public enum ProtocolEnum {
    COAP(0, "CoAP"),
    MQTT(1, "MQTT"),
    HTTP(2, "HTTP");

    private int id;
    private String description;

    ProtocolEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
