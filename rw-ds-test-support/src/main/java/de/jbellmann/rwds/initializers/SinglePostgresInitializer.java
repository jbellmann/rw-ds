package de.jbellmann.rwds.initializers;

import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@Slf4j
public class SinglePostgresInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>();

  public static Map<String, String> getProperties() {
    Startables.deepStart(Stream.of(postgres)).join();



    return Map.of(
        "spring.datasource.url", postgres.getJdbcUrl(),
        "spring.datasource.username", postgres.getUsername(),
        "spring.datasource.password", postgres.getPassword(),
        "spring.flyway.url", postgres.getJdbcUrl(),
        "spring.flyway.password", postgres.getPassword(),
        "spring.flyway.user", postgres.getUsername()
    );
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    var env = applicationContext.getEnvironment();
    env.getPropertySources().addFirst(new MapPropertySource(
        "postgresInitializer", (Map) getProperties()
    ));
  }

}
