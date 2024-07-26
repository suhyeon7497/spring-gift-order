package gift.order;

import gift.common.auth.LoginMemberDto;
import gift.common.exception.OptionException;
import gift.option.OptionErrorCode;
import gift.option.OptionRepository;
import gift.option.model.Option;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OptionRepository optionRepository;

    public OrderService(OrderRepository orderRepository, OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.optionRepository = optionRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest, LoginMemberDto loginMemberDto) {
        Option option = optionRepository.findById(orderRequest.optionId())
            .orElseThrow(() -> new OptionException(OptionErrorCode.NOT_FOUND));
        Order order = new Order(loginMemberDto.toEntity(), option, orderRequest.quantity(),
            orderRequest.message());
        order = orderRepository.save(order);
        return OrderResponse.from(order);
    }
}
