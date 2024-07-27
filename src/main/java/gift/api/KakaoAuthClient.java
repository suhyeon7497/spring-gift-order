package gift.api;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KakaoAuthClient {

    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;

    public KakaoAuthClient(KakaoProperties kakaoProperties, RestClient restClient) {
        this.kakaoProperties = kakaoProperties;
        this.restClient = restClient;
    }

    public URI getAuthorization() {
        ResponseEntity<String> response = restClient.get()
            .uri(URI.create(kakaoProperties.getAuthorizationUrl()))
            .retrieve()
            .toEntity(String.class);
        return response.getHeaders().getLocation();
    }

    public KakaoTokenResponse getKakaoTokenResponse(String authorizationCode) {
        LinkedMultiValueMap<String, String> body = createBody(authorizationCode);
        return restClient.post()
            .uri(URI.create(kakaoProperties.tokenUrl()))
            .body(body)
            .retrieve()
            .body(KakaoTokenResponse.class);
    }

    private LinkedMultiValueMap<String, String> createBody(String authorizationCode) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", kakaoProperties.grantType());
        body.add("client_id", kakaoProperties.clientId());
        body.add("redirect_uri", kakaoProperties.redirectUri());
        body.add("code", authorizationCode);
        return body;
    }

    public String getEmail(String accessToken) {
        KakaoMemberResponse response = restClient.get()
            .uri(URI.create(kakaoProperties.memberUrl()))
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoMemberResponse.class);
        return response.kakaoAccount().email();
    }
}
