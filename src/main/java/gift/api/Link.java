package gift.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Link(String webUrl,
                   String mobileWebUrl) {

}
