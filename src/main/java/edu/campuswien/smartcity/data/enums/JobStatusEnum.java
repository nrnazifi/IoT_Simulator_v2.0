package edu.campuswien.smartcity.data.enums;

public enum JobStatusEnum {
    Running(0, "Running"),
    Stopped(1, "Stopped"),
    Paused(2, "Paused"),
    NotYetRun(3, "Not yet run");

    private int id;
    private String text;

    JobStatusEnum(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
