package ru.panteleevya.secret;

import java.util.List;

public record LockboxSecretVersion(String versionId, List<LockboxKeyValue> entries) {
}
