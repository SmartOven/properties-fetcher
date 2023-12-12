package ru.panteleevya.secret;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.panteleevya.token.IamToken;

import java.net.URI;

public class LockboxSecretProvider {
    private static final Logger log = LogManager.getLogger(LockboxSecretProvider.class);

    private LockboxSecretProvider() {
    }

    /**
     * Fetching the latest version of the secret
     */
    public static LockboxSecretVersion fetchSecret(IamToken iamToken, String secretId, String secretsUrl) {
        log.info("Fetching the latest version of the secret with id={}...", secretId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + iamToken.getValue());
        URI uri = UriComponentsBuilder.fromUriString(secretsUrl)
                .pathSegment(secretId, "payload")
                .build()
                .toUri();
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestEntity,
                String.class
        );
        Gson gson = new Gson();
        LockboxSecretVersion lockboxSecretVersion = gson.fromJson(responseEntity.getBody(), LockboxSecretVersion.class);
        log.info("Successfully fetched the latest version of the secret with id={}", secretId);
        return lockboxSecretVersion;
    }
}
