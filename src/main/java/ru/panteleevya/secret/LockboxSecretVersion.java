package ru.panteleevya.secret;

import lombok.Getter;

import java.util.Properties;

@Getter
public class LockboxSecretVersion {
    private final String versionId;
    private final Properties properties;

    private LockboxSecretVersion(String versionId, Properties properties) {
        this.versionId = versionId;
        this.properties = properties;
    }

    public static LockboxSecretVersion fromDto(LockboxSecretVersionDto lockboxSecretVersionDto) {
        Properties properties = new Properties();
        for (LockboxKeyValue kvEntry : lockboxSecretVersionDto.entries()) {
            properties.setProperty(kvEntry.key(), kvEntry.textValue());
        }
        return new LockboxSecretVersion(lockboxSecretVersionDto.versionId(), properties);
    }
}
