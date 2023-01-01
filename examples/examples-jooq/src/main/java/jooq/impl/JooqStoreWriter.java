package jooq.impl;

import static readwrite.Tables.STORE;

import examples.common.StoreDto;
import examples.common.StoreWriter;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
class JooqStoreWriter implements StoreWriter {
  private final DSLContext db;
  @Override
  @Transactional
  public int writeStore(StoreDto storeDto) {
    return db.insertInto(STORE)
        .set(STORE.STORE_ID, storeDto.getStoreId())
        .set(STORE.PROJECT_ID, storeDto.getProjectId())
        .set(STORE.REQUESTED_AT, LocalDateTime.now())
        .set(STORE.SLUG, "ABCDEFG")
        .set(STORE.STATE, "STATE")
        .set(STORE.URI, "http://google.de")
        .execute();
  }
}
