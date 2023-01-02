package de.jbellmann.rwds;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class ReadWriteDataSource extends AbstractRoutingDataSource {
  protected static final ThreadLocal<Type> currentDataSource = new ThreadLocal<>();

  public ReadWriteDataSource(DataSource writeDataSource, DataSource readDataSource) {
    Map<Object, Object> dataSourceMap = new HashMap<>(2);
    dataSourceMap.put(Type.READ, readDataSource);
    dataSourceMap.put(Type.WRITE, writeDataSource);

    super.setTargetDataSources(dataSourceMap);
    super.setDefaultTargetDataSource(writeDataSource);
  }

  static void setReadOnlyDataSource(boolean isReadOnly) {
    log.debug("set datasource read-only : {}", isReadOnly);
    currentDataSource.set(isReadOnly ? Type.READ : Type.WRITE);
  }

  @Override
  protected Object determineCurrentLookupKey() {
    Type dataSourceType = currentDataSource.get();
    log.debug("Returning datasource lookup key : {}", dataSourceType);
    return dataSourceType;
  }

  protected enum Type {
    READ,
    WRITE
  }
}
