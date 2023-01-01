package jooq.impl;

import static readwrite.Tables.STORE;

import examples.common.StoreDto;
import examples.common.StoreReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class JooqStoreReader implements StoreReader {
  private final DSLContext db;

  @Override
  @Transactional(readOnly = true)
  public List<StoreDto> readStores() {
    return db.selectFrom(STORE).fetchInto(StoreDto.class);
  }
}
