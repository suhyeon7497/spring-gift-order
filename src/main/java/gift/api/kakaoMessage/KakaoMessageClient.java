package gift.api.kakaoMessage;

import gift.api.KakaoProperties;
import gift.order.Order;
import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KakaoMessageClient {

    private final KakaoProperties kakaoProperties;
    private final KakaoMessageMaker kakaoMessageMaker;
    private final RestClient restClient;

    public KakaoMessageClient(KakaoProperties kakaoProperties, KakaoMessageMaker kakaoMessageMaker,
        RestClient restClient) {
        this.kakaoProperties = kakaoProperties;
        this.kakaoMessageMaker = kakaoMessageMaker;
        this.restClient = restClient;
    }

    public void sendOrderMessage(Order order, String accessToken) {
        LinkedMultiValueMap<String, String> body = kakaoMessageMaker.createOrderMessage(order);
        restClient.post()
            .uri(URI.create(kakaoProperties.messageUrl()))
            .header("Authorization", "Bearer " + accessToken)
            .body(body)
            .retrieve();
    }


}
