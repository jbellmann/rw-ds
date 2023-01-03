package jooq;

import de.jbellmann.rwds.initializers.ReadWritePostgresInitializer;
import example.support.test.ClientRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ActiveProfiles("rwds")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {ReadWritePostgresInitializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JooqApplicationTest {

  @LocalServerPort
  private int port;

  @Test
  void testDataSource() throws InterruptedException {
    ClientRunner test = new ClientRunner();
    test.run(port);
    log.info("");
  }
}
