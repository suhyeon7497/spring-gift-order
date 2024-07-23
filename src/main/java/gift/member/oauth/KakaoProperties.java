package gift.member.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoProperties(String grantType, String clientId, String redirectUri, String tokenUrl) {

}
