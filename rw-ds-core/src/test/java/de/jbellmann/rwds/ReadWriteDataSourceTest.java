package de.jbellmann.rwds;

import de.jbellmann.rwds.ReadWriteDataSource.Type;
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

    Assertions.assertNull(rwds.determineCurrentLookupKey());
    ReadWriteDataSource.setReadOnlyDataSource(true);
    Assertions.assertSame(Type.READ, rwds.determineCurrentLookupKey());
    ReadWriteDataSource.setReadOnlyDataSource(false);
    Assertions.assertNotSame(Type.READ, rwds.determineCurrentLookupKey());
    Assertions.assertSame(Type.WRITE, rwds.determineCurrentLookupKey());
  }

}
