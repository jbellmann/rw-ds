package hibernate.impl;

import static java.util.stream.Collectors.toList;

import examples.common.StoreDto;
import examples.common.StoreReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class HibernateStoreReader implements StoreReader {

  private final StoreRepository repository;

  @Override
  @Transactional(readOnly = true)
  public List<StoreDto> readStores() {
    return repository.findAll().stream()
        .map(HibernateStoreReader::toDto)
        .collect(toList());
  }

  protected static StoreDto toDto(StoreEntity entity) {
    StoreDto dto = new StoreDto();
    dto.setStoreId(entity.getStoreId());
    dto.setProjectId(entity.getProjectId());
    dto.setId(entity.getId() != null ? String.valueOf(entity.getId()) : "NotAvailable");
    return dto;
  }
}
