package examples.common;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StandardStoreController {

  private final StandardStoreService standardStoreService;

  @GetMapping("/api/stores")
  public ResponseEntity<List<StoreDto>> getStores() {
    return ResponseEntity.ok(standardStoreService.getStores());
  }

  @GetMapping("/api/create")
  public ResponseEntity<Integer> createStore() {
    StoreDto create = StoreDto.builder()
        .storeId(UUID.randomUUID().toString())
        .projectId(UUID.randomUUID().toString())
        .build();
    return ResponseEntity.ok(standardStoreService.save(create));
  }

}
