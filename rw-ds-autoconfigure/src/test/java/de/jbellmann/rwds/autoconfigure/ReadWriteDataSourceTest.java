package de.jbellmann.rwds.autoconfigure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.jbellmann.rwds.initializers.ReadWritePostgresInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ActiveProfiles("rwds")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {FakeApplication.class})
@ContextConfiguration(initializers = {ReadWritePostgresInitializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReadWriteDataSourceTest {

  @Autowired
  private RwdsProperties rwdsProperties;

  @Test
  void contextLoads() {
    // configured in `application-rwds.yaml`
    assertTrue(rwdsProperties.getTransactionManagerCustomizations().isValidateExistingTransaction());
    // defaults to 'true'
    assertTrue(rwdsProperties.getTransactionManagerCustomizations().isGlobalRollbackOnParticipationFailure());
  }

}
