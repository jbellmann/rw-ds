package de.jbellmann.rwds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReadWriteDataSourceTest {

  @Test
  void testDataSourceRoute() {
    DataSource readDs = Mockito.mock(DataSource.class);
    DataSource writeDs = Mockito.mock(DataSource.class);
    ReadWriteDataSource rwds = new ReadWriteDataSource(writeDs, readDs);

    TransactionType transactionType = (TransactionType) rwds.determineCurrentLookupKey();
    assertNotNull(transactionType);
    assertEquals(TransactionType.WRITE, transactionType);
    ReadWriteDataSource.setReadOnlyDataSource(true);
    assertSame(TransactionType.READ, rwds.determineCurrentLookupKey());
    ReadWriteDataSource.setReadOnlyDataSource(false);
    assertNotSame(TransactionType.READ, rwds.determineCurrentLookupKey());
    assertSame(TransactionType.WRITE, rwds.determineCurrentLookupKey());
  }

}
