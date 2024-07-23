package gift.member.oauth;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/oauth/kakao/login")
    public ResponseEntity<Void> getKakaoToken(@RequestParam("code") String authorizationCode) {
        oauthService.getKakaoToken(authorizationCode);

        return ResponseEntity.ok().build();
    }
}
