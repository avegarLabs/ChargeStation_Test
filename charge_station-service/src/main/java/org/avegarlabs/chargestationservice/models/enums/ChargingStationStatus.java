package org.avegarlabs.chargestationservice.models.enums;

public enum ChargingStationStatus {
    AVAILABLE("Available"),
    IN_USE("In Use");

    private final String displayName;

    ChargingStationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}