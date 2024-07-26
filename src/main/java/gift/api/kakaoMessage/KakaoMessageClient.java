package gift.api.kakaoMessage;

import com.google.gson.Gson;
import gift.api.KakaoProperties;
import gift.order.Order;
import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KakaoMessageClient {

    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;
    private final Gson gson;

    public KakaoMessageClient(KakaoProperties kakaoProperties, RestClient restClient, Gson gson) {
        this.kakaoProperties = kakaoProperties;
        this.restClient = restClient;
        this.gson = gson;
    }

    public void sendOrderMessage(Order order, String accessToken) {
        LinkedMultiValueMap<String, String> body = createMessageByOrder(order);
        restClient.post()
            .uri(URI.create(kakaoProperties.messageUrl()))
            .header("Authorization", "Bearer " + accessToken)
            .body(body)
            .retrieve();
    }

    private LinkedMultiValueMap createMessageByOrder(Order order) {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        TextTemplate textTemplate = new TextTemplate("text", order.gerOrderMessage(),
            new Link("http://localhost:8080", "http://localhost:8080"));
        String textTemplateString = gson.toJson(textTemplate);
        System.out.println(textTemplateString);
        body.add("template_object", textTemplateString);
        return body;
    }
}
