package ru.panteleevya;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.panteleevya.secret.LockboxKeyValue;
import ru.panteleevya.secret.LockboxSecretProvider;
import ru.panteleevya.secret.LockboxSecretVersion;
import ru.panteleevya.token.IamToken;
import ru.panteleevya.token.IamTokenProvider;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final String IAM_TOKENS_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private static final String LOCKBOX_SECRETS_URL = "https://payload.lockbox.api.cloud.yandex.net/lockbox/v1/secrets";

    /**
     * Application is fetching properties from the Yandex Cloud Lockbox to the system environment.
     *
     * @param args consists of [OAuth-token, secret-id, destination]
     */
    public static void main(String[] args) {
        log.info("Application started");
        if (args.length != 3) {
            log.error("Arguments must consists of [OAuth-token, secret-id, destination], but they are {}", Arrays.toString(args));
            return;
        }
        IamToken iamToken;
        try {
            iamToken = IamTokenProvider.fetchIamToken(args[0], IAM_TOKENS_URL);
        } catch (Exception e) {
            log.error("Failed to load IAM-token", e);
            return;
        }
        LockboxSecretVersion secret;
        try {
            secret = LockboxSecretProvider.fetchSecret(iamToken, args[1], LOCKBOX_SECRETS_URL);
        } catch (Exception e) {
            log.error("Failed to load secret", e);
            return;
        }
        try (PrintWriter writer = new PrintWriter(args[2], StandardCharsets.UTF_8)) {
            for (LockboxKeyValue entry : secret.entries()) {
                writer.println(entry);
            }
        } catch (IOException e) {
            log.error("Failed to write properties to file", e);
            return;
        }
        log.info("Application finished all the jobs");
    }
}
