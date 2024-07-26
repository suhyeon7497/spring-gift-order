package gift.order;

import gift.api.KakaoMessageClient;
import gift.common.auth.LoginMemberDto;
import gift.common.exception.OptionException;
import gift.member.oauth.OauthTokenRepository;
import gift.member.oauth.model.OauthToken;
import gift.option.OptionErrorCode;
import gift.option.OptionRepository;
import gift.option.model.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OptionRepository optionRepository;
    private final OauthTokenRepository oauthTokenRepository;
    private final KakaoMessageClient kakaoMessageClient;

    public OrderService(OrderRepository orderRepository, OptionRepository optionRepository,
        OauthTokenRepository oauthTokenRepository,
        KakaoMessageClient kakaoMessageClient) {
        this.orderRepository = orderRepository;
        this.optionRepository = optionRepository;
        this.oauthTokenRepository = oauthTokenRepository;
        this.kakaoMessageClient = kakaoMessageClient;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest, LoginMemberDto loginMemberDto) {
        Option option = optionRepository.findById(orderRequest.optionId())
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        Order order = new Order(loginMemberDto.toEntity(), option, orderRequest.quantity(),
            orderRequest.message());

        option.subtract(orderRequest.quantity());
        order = orderRepository.save(order);

        OauthToken oauthToken = oauthTokenRepository.findByMemberId(loginMemberDto.getId()).orElseThrow();
        kakaoMessageClient.sendOrderMessage(order, oauthToken.getAccessToken());
        return OrderResponse.from(order);
    }
}
