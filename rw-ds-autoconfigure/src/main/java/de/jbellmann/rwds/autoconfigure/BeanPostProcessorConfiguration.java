package de.jbellmann.rwds.autoconfigure;

import de.jbellmann.rwds.TransactionManagerBeanPostProcessor;
import de.jbellmann.rwds.autoconfigure.conditions.ConditionalOnReadWriteDataSourceConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnReadWriteDataSourceConfigProperties
class BeanPostProcessorConfiguration {
  @Bean
  static TransactionManagerBeanPostProcessor transactionManagerBeanPostProcessor() {
    return new TransactionManagerBeanPostProcessor();
  }

}
