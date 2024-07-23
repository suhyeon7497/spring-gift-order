package gift.member.oauth;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class OauthService {
    private final KakaoProperties kakaoProperties;

    private final RestClient restClient;

    public OauthService(KakaoProperties kakaoProperties, RestClient restClient) {
        this.kakaoProperties = kakaoProperties;
        this.restClient = restClient;
    }

    public void getKakaoToken(String authorizationCode) {
        LinkedMultiValueMap<String, String> body = createBody(authorizationCode);

        ResponseEntity<KakaoTokenResponse> response = restClient.post()
            .uri(URI.create(kakaoProperties.tokenUrl()))
            .body(body)
            .retrieve()
            .toEntity(KakaoTokenResponse.class);
    }

    private LinkedMultiValueMap<String, String> createBody(String authorizationCode) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", kakaoProperties.grantType());
        body.add("client_id", kakaoProperties.clientId());
        body.add("redirect_uri", kakaoProperties.redirectUri());
        body.add("code", authorizationCode);
        return body;
    }
}
