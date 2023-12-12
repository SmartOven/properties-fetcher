package ru.panteleevya;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.panteleevya.secret.LockboxSecretProvider;
import ru.panteleevya.secret.LockboxSecretVersion;
import ru.panteleevya.token.IamToken;
import ru.panteleevya.token.IamTokenProvider;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final String IAM_TOKENS_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private static final String LOCKBOX_SECRETS_URL = "https://payload.lockbox.api.cloud.yandex.net/lockbox/v1/secrets";

    /**
     * Application is fetching properties from the Yandex Cloud Lockbox to the system environment.
     *
     * @param args consists of [OAuth-token, secret-id]
     */
    public static void main(String[] args) {
        log.info("Application started");
        if (args.length != 2) {
            log.error("Arguments must consists of [OAuth-token, secret-id]");
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
        System.setProperties(secret.getProperties());
        log.info("Application finished all the jobs");
    }
}
