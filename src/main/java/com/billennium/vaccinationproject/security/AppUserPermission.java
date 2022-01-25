package com.billennium.vaccinationproject.security;


public enum AppUserPermission {
    USER_WRITE("user-write"),
    USER_READ("user-read"),
    FACILITY_READ("facility-read"),
    FACILITY_WRITE("facility-write"),
    VACCINE_READ("vaccine-read"),
    VACCINE_WRITE("vaccine-write"),
    VISIT_READ("visit-read"),
    VISIT_WRITE("visit-write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
