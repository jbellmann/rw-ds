package hibernate.impl;

import examples.common.StoreReader;
import examples.common.StoreWriter;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Configuration
@EnableJpaRepositories
public class HibernateAppConfig {

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> transactionManagerCustomizer() {
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setValidateExistingTransaction(true);
  }

  @Bean
  StoreReader storeReader(StoreRepository repository) {
    return new HibernateStoreReader(repository);
  }

  @Bean
  StoreWriter storeWriter(StoreRepository repository) {
    return new HibernateStoreWriter(repository);
  }
}
