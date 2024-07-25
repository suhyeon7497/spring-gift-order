package gift.member.oauth;

import gift.api.KakaoAuthClient;
import gift.api.KakaoMemberResponse;
import gift.api.KakaoTokenResponse;
import gift.common.utils.TokenProvider;
import gift.member.MemberRepository;
import gift.member.model.Member;
import gift.member.oauth.model.OauthToken;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

@Service
public class OauthService {

    private final KakaoAuthClient kakaoAuthClient;

    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;

    private final OauthTokenRepository oauthTokenRepository;

    public OauthService(KakaoAuthClient kakaoAuthClient, TokenProvider tokenProvider, MemberRepository memberRepository,
        OauthTokenRepository oauthTokenRepository) {
        this.kakaoAuthClient = kakaoAuthClient;
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
        this.oauthTokenRepository = oauthTokenRepository;
    }

    public URI getKakaoAuthorization() {
        return kakaoAuthClient.getAuthorization();
    }

    @Transactional
    public String loginByKakao(String authorizationCode) {
        KakaoTokenResponse kakaoTokenReponse = kakaoAuthClient.getKakaoTokenResponse(authorizationCode);
        String email = kakaoAuthClient.getEmail(kakaoTokenReponse.accessToken());
        Member member = saveToken(email, kakaoTokenReponse);
        return tokenProvider.generateToken(member);
    }

    private Member saveToken(String email, KakaoTokenResponse kakaoTokenReponse) {
        if (oauthTokenRepository.existsByProviderAndEmail("kakao", email)) {
            OauthToken oauthToken = oauthTokenRepository.findByProviderAndEmail("kakao",
                email);
            oauthToken.updateToken(kakaoTokenReponse.accessToken(),
                kakaoTokenReponse.refreshToken());
            return oauthToken.getMember();
        }

        Member member = new Member(email, "", "nickname", "Member");
        member = memberRepository.save(member);
        OauthToken oauthToken = new OauthToken("kakao", email,
            kakaoTokenReponse.accessToken(), kakaoTokenReponse.refreshToken(), member);
        oauthTokenRepository.save(oauthToken);
        return member;
    }

}
