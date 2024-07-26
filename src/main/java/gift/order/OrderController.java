package gift.order;

import gift.common.auth.LoginMember;
import gift.common.auth.LoginMemberDto;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest,
        @LoginMember LoginMemberDto loginMemberDto) {
        return ResponseEntity.created(URI.create("/api/orders/1")).body(null);
    }
}
