package example.support.initializers;

import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class ReadWritePostgresInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  static Network network = Network.newNetwork();
  static GenericContainer<?> writePostgres = new GenericContainer<>(
      DockerImageName.parse("bitnami/postgresql:14"))
      .withEnv(Map.of(
          "POSTGRESQL_REPLICATION_MODE", "master",
          "POSTGRESQL_REPLICATION_USER", "repl_user",
          "POSTGRESQL_REPLICATION_PASSWORD", "repl_password",
          "POSTGRESQL_USERNAME", "postgres",
          "POSTGRESQL_PASSWORD", "postgres",
          "POSTGRESQL_DATABASE", "postgres",
          "ALLOW_EMPTY_PASSWORD", "yes"
      ))
      .withExposedPorts(5432)
      .withNetwork(network)
      .withNetworkAliases("postgresql-write");

  static GenericContainer<?> readPostgres = new GenericContainer<>(
      DockerImageName.parse("bitnami/postgresql:14"))
      .withEnv(Map.of(
          "POSTGRESQL_REPLICATION_MODE", "slave",
          "POSTGRESQL_REPLICATION_USER", "repl_user",
          "POSTGRESQL_REPLICATION_PASSWORD", "repl_password",
          "POSTGRESQL_MASTER_HOST", "postgresql-write",
          "POSTGRESQL_MASTER_PORT", "5432",
          "POSTGRESQL_USERNAME", "postgres",
          "POSTGRESQL_PASSWORD", "postgres",
          "ALLOW_EMPTY_PASSWORD", "yes"
      ))
      .withExposedPorts(5432)
      .withNetwork(network)
      .withNetworkAliases("postgresql-read");
  public static Map<String, String> getProperties() {
    Startables.deepStart(Stream.of(writePostgres, readPostgres)).join();

    String urlTemplate = "jdbc:postgresql://localhost:%s/postgres";

    return Map.of(
        "rwds.datasource.write.url", String.format(urlTemplate, writePostgres.getMappedPort(5432)),
        "rwds.datasource.write.username", "postgres",
        "rwds.datasource.write.password", "postgres",
        "rwds.datasource.read.url", String.format(urlTemplate, readPostgres.getMappedPort(5432)),
        "rwds.datasource.read.username", "postgres",
        "rwds.datasource.read.password", "postgres"
    );
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    var env = applicationContext.getEnvironment();
    env.getPropertySources().addFirst(new MapPropertySource(
        "readWritePostgresInitializer", (Map) getProperties()
    ));
  }

}
