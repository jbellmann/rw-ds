# Read-Write-DataSource Spring-Boot Starter

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jbellmann_rw-ds&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jbellmann_rw-ds)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jbellmann_rw-ds&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jbellmann_rw-ds)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jbellmann_rw-ds&metric=bugs)](https://sonarcloud.io/summary/new_code?id=jbellmann_rw-ds)
[![Maven Central](https://img.shields.io/maven-central/v/de.jbellmann.rwds/rw-ds-parent.svg)](https://maven-badges.herokuapp.com/maven-central/de.jbellmann.rwds/rw-ds-parent)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/jbellmann/rw-ds/main/LICENSE)

## Installation

Add the following dependency to your project:

```xml
<dependency>
  <groupId>de.jbellmann.rwds</groupId>
  <artifactId>rw-ds-spring-boot-starter</artifactId>
  <version>${project.version}</version>
</dependency>
```

Supports:
- Spring-Boot 2.7.x
- Spring-Boot 3.0.x

## Configuration

The library follows spring-boots howto [configuring multiple datasources](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-access.configure-two-datasources).
Configuration section respects settings for [HikariCP](https://github.com/brettwooldridge/HikariCP#frequently-used).

```yaml
rwds:
  datasource:
    write:
      driver-class-name: org.postgresql.Driver
      url: jdbc:postgresql://localhost:{WRITE_PORT}/postgres
      username: postgres
      password: postgres
      configuration: # HikariCP config options
        maximum-pool-size: 5
        auto-commit: false # has to be 'false' when used with hibernate

    read:
      driver-class-name: org.postgresql.Driver
      url: jdbc:postgresql://localhost:{READ_PORT}/postgres
      username: postgres
      password: postgres
      configuration: # HikariCP config options
        maximum-pool-size: 5
        auto-commit: false # has to be 'false' when used with hibernate
```

## Examples

This project provides some example applications.

- [JOOQ](https://github.com/jbellmann/rw-ds/tree/main/examples/examples-jooq)
- Spring-Data-JPA 
  - [with spring-boot-2](https://github.com/jbellmann/rw-ds/tree/main/examples/examples-hibernate-spring-boot-2) (`import javax.persistence.Entity;`)
  - [with spring-boot-3](https://github.com/jbellmann/rw-ds/tree/main/examples/examples-hibernate-spring-boot-3) (`import jakarta.persistence.Entity;`)

## Build

This project uses [Build Profiles](https://maven.apache.org/guides/introduction/introduction-to-profiles.html) to support different versions of spring-boot.
- spring-boot-2 (build against Spring-Boot version 2.7.x, active by default)
- spring-boot-3 (build against Spring-Boot version 3.0.x)
- examples (enables `examples` sub-modules to be build too)

```shell
./mvnw verify # spring-boot-2, active by default
```

```shell
./mvnw verify -Pspring-boot-3 # will activate spring-boot-3 profile, deactivates spring-boot-2 profile
```

```shell
./mvnw verify -Pspring-boot-2,examples # will activate spring-boot-2 profile, activates examples modules to be build
```

```shell
./mvnw verify -Pspring-boot-3,examples # will activate spring-boot-2 profile, activates examples modules to be build
```

:warning: `examples`-modules contain integration tests spawning PostgreSQL instances in [Docker](https://www.docker.com/) via [TestContainers](https://testcontainers.org). A working Docker installation is required to be in place.
