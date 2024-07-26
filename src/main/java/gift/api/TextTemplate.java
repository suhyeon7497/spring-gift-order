package gift.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TextTemplate(String objectType,
                           String text,
                           Link link) {
}
