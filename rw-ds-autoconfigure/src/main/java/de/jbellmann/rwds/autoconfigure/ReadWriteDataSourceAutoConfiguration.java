package de.jbellmann.rwds.autoconfigure;

import com.zaxxer.hikari.HikariDataSource;
import de.jbellmann.rwds.ReadWriteDataSource;
import de.jbellmann.rwds.TransactionManagerBeanPostProcessor;
import de.jbellmann.rwds.autoconfigure.conditions.ConditionalOnReadWriteDataSourceConfigProperties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnReadWriteDataSourceConfigProperties
public class ReadWriteDataSourceAutoConfiguration {

  public static final String PROPERTY_RWDS_DS_WRITE = "rwds.datasource.write";
  public static final String PROPERTY_RWDS_DS_READ = "rwds.datasource.read";
  public static final String PROPERTY_RWDS_DS_WRITE_CONFIGURATION = "rwds.datasource.write.configuration";
  public static final String PROPERTY_RWDS_DS_READ_CONFIGURATION = "rwds.datasource.read.configuration";

  // Beans
  public static final String RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN = "rwdsWriteDataSourceProperties";
  public static final String RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN = "rwdsReadDataSourceProperties";
  public static final String RWDS_WRITE_DATA_SOURCE_BEAN = "rwdsWriteDataSource";
  public static final String RWDS_READ_DATA_SOURCE_BEAN = "rwdsReadDataSource";
  public static final String RWDS_DATA_SOURCE_BEAN = "rwdsDataSource";

  @Primary
  @Bean(name = RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN)
  @ConfigurationProperties(PROPERTY_RWDS_DS_WRITE)
  public DataSourceProperties writeDataSourceProperties() {
    log.debug("Load WriteDataSourceProperties ...");
    return new DataSourceProperties();
  }

  @Bean(name = RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN)
  @ConfigurationProperties(PROPERTY_RWDS_DS_READ)
  public DataSourceProperties readDataSourceProperties() {
    log.debug("Load ReadDataSourceProperties ...");
    return new DataSourceProperties();
  }

  @Bean(name = RWDS_WRITE_DATA_SOURCE_BEAN)
  @ConfigurationProperties(PROPERTY_RWDS_DS_WRITE_CONFIGURATION)
  public HikariDataSource writeDataSource(@Qualifier(RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN) DataSourceProperties writeDataSourceProperties) {
    log.debug("Create WriteDataSource ...");
    return writeDataSourceProperties
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean(name = RWDS_READ_DATA_SOURCE_BEAN)
  @ConfigurationProperties(PROPERTY_RWDS_DS_READ_CONFIGURATION)
  public HikariDataSource readDataSource(@Qualifier(RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN) DataSourceProperties readDataSourceProperties) {
    log.debug("Create ReadDataSource ...");
    return readDataSourceProperties
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Primary
  @Bean(name = RWDS_DATA_SOURCE_BEAN)
  public DataSource routingDataSource(
      @Qualifier(RWDS_WRITE_DATA_SOURCE_BEAN) DataSource writeDataSource,
      @Qualifier(RWDS_READ_DATA_SOURCE_BEAN) DataSource readDataSource
  ) {
    log.debug("Create ReadWriteDataSource ...");
    return new ReadWriteDataSource(writeDataSource, readDataSource);
  }

  @Configuration
  @ConditionalOnReadWriteDataSourceConfigProperties
  static class BeanPostProcessorConfiguration {
    @Bean
    public static TransactionManagerBeanPostProcessor transactionManagerBeanPostProcessor() {
      return new TransactionManagerBeanPostProcessor();
    }
  }
}
