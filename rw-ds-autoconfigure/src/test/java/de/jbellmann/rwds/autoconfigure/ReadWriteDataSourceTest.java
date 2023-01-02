package de.jbellmann.rwds.autoconfigure;

import de.jbellmann.rwds.initializers.ReadWritePostgresInitializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

  @Test
  void contextLoads() {
    Assertions.assertTrue(true);
  }

}
