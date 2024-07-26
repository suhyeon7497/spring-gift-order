package gift.order;

import java.time.LocalDateTime;

public record OrderResponse(Long id,
                            Long optionId,
                            Integer quantity,
                            LocalDateTime optionDateTime,
                            String message) {

}
