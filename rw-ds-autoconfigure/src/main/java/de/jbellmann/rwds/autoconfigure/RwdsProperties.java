package de.jbellmann.rwds.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "rwds")
public class RwdsProperties {
  @NestedConfigurationProperty
  private TransactionManagerCustomizations transactionManagerCustomizations = new TransactionManagerCustomizations();

  /**
   * Uses default settings from @{@link org.springframework.transaction.support.AbstractPlatformTransactionManager}
   */
  @Data
  static class TransactionManagerCustomizations {
    private boolean validateExistingTransaction = false;
    private boolean nestedTransactionAllowed = false;
    private boolean failEarlyOnGlobalRollbackOnly = false;
    private boolean rollbackOnCommitFailure = false;
    private boolean globalRollbackOnParticipationFailure = true;
  }

}
