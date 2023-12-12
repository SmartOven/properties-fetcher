package ru.panteleevya.secret;

import java.util.List;

public record LockboxSecretVersionDto(String versionId, List<LockboxKeyValue> entries) {
}
