package com.khalchukov.model;

public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN,
    GENDERLESS;


    public static Gender fromString(String raw) {
        if (raw == null || raw.isBlank()) {
            return UNKNOWN;
        }
        return switch (raw.trim().toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            case "genderless" -> GENDERLESS;
            default -> UNKNOWN;
        };
    }
}