package hibernate.impl;

import examples.common.StoreDto;
import examples.common.StoreWriter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class HibernateStoreWriter implements StoreWriter {

  private final StoreRepository repository;

  @Override
  @Transactional
  public int writeStore(StoreDto storeDto) {
    StoreEntity entity = new StoreEntity();
    entity.setStoreId(storeDto.getStoreId());
    entity.setProjectId(storeDto.getProjectId());
    entity.setSlug("ABCDE");
    entity.setState("STATE");
    entity.setUri("https://google.de");
    entity.setRequestedAt(LocalDateTime.now());
    repository.save(entity);

    return 1;
  }
}
