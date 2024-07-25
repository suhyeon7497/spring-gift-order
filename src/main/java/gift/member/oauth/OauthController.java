package gift.member.oauth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/kakao/login")
public class OauthController {

    private final OauthService oauthService;

    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping
    public ResponseEntity<Void> getAuthorization() {
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(oauthService.getKakaoAuthorization()).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<String> getKakaoToken(@RequestParam("code") String authorizationCode) {
        String accessToken = oauthService.loginByKakao(authorizationCode);
        return ResponseEntity.ok(accessToken);
    }
}
