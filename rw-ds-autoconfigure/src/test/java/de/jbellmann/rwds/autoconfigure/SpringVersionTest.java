package de.jbellmann.rwds.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;

@Slf4j
public class SpringVersionTest {

  @Test
  void figureOutSpringVersion() {
    String springVersion = SpringVersion.getVersion();
    log.info("Spring-Version : {}", springVersion);
  }

  @Test
  void figureOutSpringBootVersion() {
    String springBootVersion = SpringBootVersion.getVersion();
    log.info("SpringBootVersion : {}", springBootVersion);
  }

}
