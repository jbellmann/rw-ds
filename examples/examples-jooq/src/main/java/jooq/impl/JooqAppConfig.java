package jooq.impl;

import examples.common.StoreReader;
import examples.common.StoreWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Slf4j
@Configuration
@EnableTransactionManagement
class JooqAppConfig {

  @Bean
  PlatformTransactionManagerCustomizer<AbstractPlatformTransactionManager> transactionManagerCustomizer() {
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setValidateExistingTransaction(true);
  }

  @Bean
  StoreWriter storeWriter(DSLContext dslContext) {
    return new JooqStoreWriter(dslContext);
  }

  @Bean
  StoreReader storeReader(DSLContext dslContext) {
    return new JooqStoreReader(dslContext);
  }
}
