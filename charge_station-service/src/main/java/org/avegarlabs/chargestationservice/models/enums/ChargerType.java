package org.avegarlabs.chargestationservice.models.enums;

public enum ChargerType {
    AC("AC Charger"),
    DC("DC Charger");

    private final String displayName;

    ChargerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
