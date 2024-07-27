package gift.option;

import gift.option.model.OptionRequest;
import gift.option.model.OptionResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/api/products/{id}/options")
    public ResponseEntity<List<OptionResponse>> getAllOption(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(optionService.getOptions(productId));
    }

    @PostMapping("/api/products/{id}/options")
    public ResponseEntity<Void> addOption(@PathVariable("id") Long productId,
        @Valid @RequestBody OptionRequest.Create optionCreate) {
        return ResponseEntity.created(
            URI.create("/api/options/" + optionService.addOption(productId, optionCreate))).build();
    }

    @PutMapping("/api/options/{optionId}")
    public ResponseEntity<Void> updateOption(@PathVariable("optionId") Long optionId,
        @Valid @RequestBody OptionRequest.Update optionUpdate) {
        optionService.updateOption(optionId, optionUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/options/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable("optionId") Long optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.ok().build();
    }
}
