package hibernate;

import example.support.initializers.ReadWritePostgresInitializer;
import example.support.test.ClientRunner;
import example.support.test.ClientRunnerResult;
import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ActiveProfiles("rwds")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {ReadWritePostgresInitializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class HibernateApplicationTest {

  @Autowired
  @Qualifier("writeDataSourceQueryCountListener")
  private DataSourceQueryCountListener writeDataSourceQueryCountListener;

  @Autowired
  @Qualifier("readDataSourceQueryCountListener")
  private DataSourceQueryCountListener readDataSourceQueryCountListener;

  @LocalServerPort
  private int port;

  @Test
  void testDataSource() {
    log.info("WRITE_DATASOURCE_COUNT : {}", getTotalWrites(writeDataSourceQueryCountListener));
    log.info("READ_DATASOURCE_COUNT : {}", getTotalReads(readDataSourceQueryCountListener));

    resetWritesCountListener(writeDataSourceQueryCountListener);
    resetReadsCountListener(readDataSourceQueryCountListener);

    ClientRunner test = new ClientRunner();
    ClientRunnerResult result = test.run(port);

    log.info("WRITE_DATASOURCE_COUNT : {}", getTotalWrites(writeDataSourceQueryCountListener));
    log.info("READ_DATASOURCE_COUNT : {}", getTotalReads(readDataSourceQueryCountListener));

    Assertions.assertThat(result.getWriteExecutionCount()).isEqualTo(getTotalWrites(writeDataSourceQueryCountListener));
    Assertions.assertThat(result.getReadExecutionCount()).isEqualTo(getTotalReads(readDataSourceQueryCountListener));
  }

  protected static void resetWritesCountListener(DataSourceQueryCountListener listener) {
    resetDataSourceQueryCountListener(listener, "WRITE_DATASOURCE");
  }

  protected static void resetReadsCountListener(DataSourceQueryCountListener listener) {
    resetDataSourceQueryCountListener(listener, "READ_DATASOURCE");
  }

  protected static void resetDataSourceQueryCountListener(DataSourceQueryCountListener listener, String dataSourceName) {
    listener.getQueryCountStrategy().getOrCreateQueryCount(dataSourceName).setTotal(0);
  }

  protected static long getTotalWrites(DataSourceQueryCountListener listener) {
    return getTotal(listener,"WRITE_DATASOURCE");
  }

  protected static long getTotalReads(DataSourceQueryCountListener listener) {
    return getTotal(listener,"READ_DATASOURCE");
  }

  protected static long getTotal(DataSourceQueryCountListener listener, String dataSourceName) {
    return listener.getQueryCountStrategy().getOrCreateQueryCount(dataSourceName).getTotal();
  }
}
