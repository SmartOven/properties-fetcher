package ru.panteleevya.token;

import com.google.gson.Gson;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Getter
public class IamTokenProvider {
    private static final Logger log = LogManager.getLogger(IamTokenProvider.class);

    private IamTokenProvider() {
    }

    public static IamToken fetchIamToken(String oauthToken, String tokensUrl) {
        log.info("Fetching IAM-token started...");
        RestTemplate restTemplate = new RestTemplate();
        String requestBody = String.format("{\"yandexPassportOauthToken\":\"%s\"}", oauthToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                tokensUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        Gson gson = new Gson();
        IamTokenDto iamTokenDto = gson.fromJson(responseEntity.getBody(), IamTokenDto.class);
        IamToken iamToken = IamToken.fromDto(iamTokenDto);
        log.info("Successfully fetched IAM-token");
        return iamToken;
    }
}
