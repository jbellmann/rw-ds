package de.jbellmann.rwds.initializers;

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
  public static final String POSTGRES = "postgres";
  static GenericContainer<?> writePostgres = new GenericContainer<>(
      DockerImageName.parse("bitnami/postgresql:14"))
      .withEnv(Map.of(
          "POSTGRESQL_REPLICATION_MODE", "master",
          "POSTGRESQL_REPLICATION_USER", "repl_user",
          "POSTGRESQL_REPLICATION_PASSWORD", "repl_password",
          "POSTGRESQL_USERNAME", POSTGRES,
          "POSTGRESQL_PASSWORD", POSTGRES,
          "POSTGRESQL_DATABASE", POSTGRES,
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
          "POSTGRESQL_USERNAME", POSTGRES,
          "POSTGRESQL_PASSWORD", POSTGRES,
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
        "rwds.datasource.write.username", POSTGRES,
        "rwds.datasource.write.password", POSTGRES,
        "rwds.datasource.read.url", String.format(urlTemplate, readPostgres.getMappedPort(5432)),
        "rwds.datasource.read.username", POSTGRES,
        "rwds.datasource.read.password", POSTGRES
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
