package de.jbellmann.rwds.autoconfigure.conditions;

import de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class OnReadWriteDataSourceConfigProperties extends AllNestedConditions {

  OnReadWriteDataSourceConfigProperties() {
    super(ConfigurationPhase.REGISTER_BEAN);
  }

  @ConditionalOnProperty(prefix = ReadWriteDataSourceConfiguration.PROPERTY_RWDS_DS_WRITE, name = "url")
  static class OnRwdsWriteDataSourceUrl {}

  @ConditionalOnProperty(prefix = ReadWriteDataSourceConfiguration.PROPERTY_RWDS_DS_READ, name = "url")
  static class OnRwdsReadDataSourceUrl {}
}
