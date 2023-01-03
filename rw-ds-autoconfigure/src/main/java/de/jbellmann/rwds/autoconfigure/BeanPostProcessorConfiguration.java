package de.jbellmann.rwds.autoconfigure;

import de.jbellmann.rwds.TransactionManagerBeanPostProcessor;
import de.jbellmann.rwds.autoconfigure.conditions.ConditionalOnReadWriteDataSourceConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnReadWriteDataSourceConfigProperties
public class BeanPostProcessorConfiguration {
  @Bean
  public static TransactionManagerBeanPostProcessor transactionManagerBeanPostProcessor() {
    return new TransactionManagerBeanPostProcessor();
  }

}
