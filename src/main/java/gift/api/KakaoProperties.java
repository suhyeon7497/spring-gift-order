package gift.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(String grantType,
                              String clientId,
                              String authorization,
                              String redirectUri,
                              String tokenUrl,
                              String memberUrl,
                              String messageUrl) {

    public String getAuthorizationUrl() {
        return authorization
            + "&redirect_uri=" + redirectUri
            + "&client_id=" + clientId;
    }
}
