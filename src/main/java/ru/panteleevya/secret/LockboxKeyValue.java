package ru.panteleevya.secret;

public record LockboxKeyValue(String key, String textValue) {
    @Override
    public String toString() {
        return String.format("%s=%s", key, textValue);
    }
}
