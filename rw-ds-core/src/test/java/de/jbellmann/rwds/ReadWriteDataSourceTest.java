package de.jbellmann.rwds;

import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReadWriteDataSourceTest {

  @Test
  void testDataSourceRoute() {
    DataSource readDs = Mockito.mock(DataSource.class);
    DataSource writeDs = Mockito.mock(DataSource.class);
    ReadWriteDataSource rwds = new ReadWriteDataSource(writeDs, readDs);

    Assertions.assertNotNull(rwds.determineCurrentLookupKey());
    ReadWriteDataSource.setReadOnlyDataSource(true);
    Assertions.assertSame(TransactionType.READ, rwds.determineCurrentLookupKey());
    ReadWriteDataSource.setReadOnlyDataSource(false);
    Assertions.assertNotSame(TransactionType.READ, rwds.determineCurrentLookupKey());
    Assertions.assertSame(TransactionType.WRITE, rwds.determineCurrentLookupKey());
  }

}
