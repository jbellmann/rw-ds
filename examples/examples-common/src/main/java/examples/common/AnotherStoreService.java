package examples.common;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnotherStoreService {
  private final StoreReader storeReader;
  private final StoreWriter storeWriter;

  @Transactional(readOnly = true)
  public List<StoreDto> getStores() {
    log.info("getStores() - Current-Transaction is readOnly = {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    return storeReader.readStores();
  }

  @Transactional(readOnly = true)
  public int save(StoreDto storeDto) {
    log.info("save() - Current-Transaction is readOnly = {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
    return storeWriter.writeStore(storeDto);
  }
}
