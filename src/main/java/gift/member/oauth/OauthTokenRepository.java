package gift.member.oauth;

import gift.member.oauth.model.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthTokenRepository extends JpaRepository<OauthToken, Long> {

    OauthToken findByProviderAndEmail(String provider, String email);

    boolean existsByProviderAndEmail(String provider, String email);
}
