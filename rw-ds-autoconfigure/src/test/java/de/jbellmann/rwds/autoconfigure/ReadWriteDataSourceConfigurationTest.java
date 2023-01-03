package de.jbellmann.rwds.autoconfigure;

import static de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration.RWDS_DATA_SOURCE_BEAN;
import static de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration.RWDS_READ_DATA_SOURCE_BEAN;
import static de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration.RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN;
import static de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration.RWDS_WRITE_DATA_SOURCE_BEAN;
import static de.jbellmann.rwds.autoconfigure.ReadWriteDataSourceConfiguration.RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN;
import static org.assertj.core.api.Assertions.assertThat;

import de.jbellmann.rwds.TransactionManagerBeanPostProcessor;
import de.jbellmann.rwds.initializers.ReadWritePostgresInitializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class ReadWriteDataSourceConfigurationTest {

  private final ApplicationContextRunner runner = new ApplicationContextRunner();

  @Test
  void conditionOnReadWriteDataSourceDoesNotMatch() {
    this.runner
        .withUserConfiguration(
            ReadWriteDataSourceConfiguration.class,
            BeanPostProcessorConfiguration.class,
            TransactionManagerCustomizersConfiguration.class)
        .run(context -> {
          assertThat(context).doesNotHaveBean(RwdsProperties.class);
          assertThat(context).doesNotHaveBean(RWDS_READ_DATA_SOURCE_BEAN);
          assertThat(context).doesNotHaveBean(RWDS_WRITE_DATA_SOURCE_BEAN);
          assertThat(context).doesNotHaveBean(RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN);
          assertThat(context).doesNotHaveBean(RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN);
          assertThat(context).doesNotHaveBean(RWDS_DATA_SOURCE_BEAN);
          assertThat(context).doesNotHaveBean(TransactionManagerBeanPostProcessor.class);
        });
  }


  @Test
  @Disabled("Have to investigate, how this can be tested with ApplicationContextRunner")
  void conditionOnReadWriteDataSourceDoesMatch() {
    ApplicationContextRunner runner = new ApplicationContextRunner()
        .withInitializer(new ReadWritePostgresInitializer())
        .withInitializer(new ConfigDataApplicationContextInitializer())
        .withInitializer(new ConditionEvaluationReportLoggingListener())
        .withPropertyValues("spring.config.location=classpath:/config/application-rwds.yaml")
        .withConfiguration(AutoConfigurations.of(ReadWriteDataSourceConfiguration.class));
    runner
        .run(context -> {
          assertThat(context).hasBean(RWDS_READ_DATA_SOURCE_BEAN);
          assertThat(context).hasBean(RWDS_WRITE_DATA_SOURCE_BEAN);
          assertThat(context).hasBean(RWDS_READ_DATA_SOURCE_PROPERTIES_BEAN);
          assertThat(context).hasBean(RWDS_WRITE_DATA_SOURCE_PROPERTIES_BEAN);
          assertThat(context).hasBean(RWDS_DATA_SOURCE_BEAN);
          assertThat(context).hasSingleBean(TransactionManagerBeanPostProcessor.class);
        });
  }
}
