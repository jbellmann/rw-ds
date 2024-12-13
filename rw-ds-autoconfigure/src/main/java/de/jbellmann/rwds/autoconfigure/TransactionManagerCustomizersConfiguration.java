package de.jbellmann.rwds.autoconfigure;

import de.jbellmann.rwds.autoconfigure.conditions.ConditionalOnReadWriteDataSourceConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnReadWriteDataSourceConfigProperties
@EnableConfigurationProperties({RwdsProperties.class})
class TransactionManagerCustomizersConfiguration {

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> setValidateTransaction(RwdsProperties rwdsProperties) {
    log.info("Set 'validateExistingTransaction' to '{}' via customizer ...", rwdsProperties.getTransactionManagerCustomizations().isValidateExistingTransaction());
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setValidateExistingTransaction(rwdsProperties.getTransactionManagerCustomizations().isValidateExistingTransaction());
  }

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> setNestedTransactionAllowed(RwdsProperties rwdsProperties) {
    log.info("Set 'nestedTransactionAllowed' to '{}' via customizer ...", rwdsProperties.getTransactionManagerCustomizations().isNestedTransactionAllowed());
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setNestedTransactionAllowed(rwdsProperties.getTransactionManagerCustomizations().isNestedTransactionAllowed());
  }

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> setFailEarlyOnGlobalRollbackOnly(RwdsProperties rwdsProperties) {
    log.info("Set 'failEarlyOnGlobalRollbackOnly' to '{}' via customizer ...", rwdsProperties.getTransactionManagerCustomizations().isFailEarlyOnGlobalRollbackOnly());
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setFailEarlyOnGlobalRollbackOnly(rwdsProperties.getTransactionManagerCustomizations().isFailEarlyOnGlobalRollbackOnly());
  }

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> setRollbackOnCommitFailure(RwdsProperties rwdsProperties) {
    log.info("Set 'rollbackOnCommitFailure' to '{}' via customizer ...", rwdsProperties.getTransactionManagerCustomizations().isRollbackOnCommitFailure());
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setRollbackOnCommitFailure(rwdsProperties.getTransactionManagerCustomizations().isRollbackOnCommitFailure());
  }

  @Bean
  TransactionManagerCustomizer<AbstractPlatformTransactionManager> setglobalRollbackOnParticipationFailure(RwdsProperties rwdsProperties) {
    log.info("Set 'globalRollbackOnParticipationFailure' to '{}' via customizer ...", rwdsProperties.getTransactionManagerCustomizations().isGlobalRollbackOnParticipationFailure());
    return (AbstractPlatformTransactionManager transactionManager) -> transactionManager
        .setGlobalRollbackOnParticipationFailure(rwdsProperties.getTransactionManagerCustomizations().isGlobalRollbackOnParticipationFailure());
  }

}
