package de.jbellmann.rwds;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class ReadWriteDataSource extends AbstractRoutingDataSource {
  protected static final CurrentTransactionType CURRENT_TRANSACTION_TYPE = new CurrentTransactionType();

  public ReadWriteDataSource(DataSource writeDataSource, DataSource readDataSource) {
    Map<Object, Object> dataSourceMap = new HashMap<>(2);
    dataSourceMap.put(TransactionType.READ, readDataSource);
    dataSourceMap.put(TransactionType.WRITE, writeDataSource);

    super.setTargetDataSources(dataSourceMap);
    super.setDefaultTargetDataSource(writeDataSource);
  }

  static void setReadOnlyDataSource(boolean isReadOnly) {
    log.debug("set datasource read-only : {}", isReadOnly);
    CURRENT_TRANSACTION_TYPE.set(isReadOnly ? TransactionType.READ : TransactionType.WRITE);
  }

  @Override
  protected Object determineCurrentLookupKey() {
    TransactionType dataSourceTransactionType = CURRENT_TRANSACTION_TYPE.get();
    log.debug("Returning datasource lookup key : {}", dataSourceTransactionType);
    return dataSourceTransactionType;
  }
}
