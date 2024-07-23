package gift.member.oauth;


import java.net.URI;
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
            .location(oauthService.getAuthorization()).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> getKakaoToken(@RequestParam("code") String authorizationCode) {
        oauthService.getKakaoToken(authorizationCode);
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
            .location(URI.create("http://localhost:8080")).build();
    }
}
