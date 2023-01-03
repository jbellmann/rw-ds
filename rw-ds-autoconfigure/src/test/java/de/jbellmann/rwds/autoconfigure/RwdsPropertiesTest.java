package de.jbellmann.rwds.autoconfigure;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class RwdsPropertiesTest {

  @Test
  void verifyAbstractTransactionManagerDefaults() {
    VerificationTM v = new VerificationTM();
    RwdsProperties.TransactionManagerCustomizations config = new RwdsProperties.TransactionManagerCustomizations();
    assertSame(v.isRollbackOnCommitFailure(),config.isRollbackOnCommitFailure());
    assertSame(v.isNestedTransactionAllowed(),config.isNestedTransactionAllowed());
    assertSame(v.isGlobalRollbackOnParticipationFailure(),config.isGlobalRollbackOnParticipationFailure());
    assertSame(v.isFailEarlyOnGlobalRollbackOnly(),config.isFailEarlyOnGlobalRollbackOnly());
    assertSame(v.isValidateExistingTransaction(),config.isValidateExistingTransaction());
  }
  protected static class VerificationTM extends AbstractPlatformTransactionManager {

    @Override
    protected Object doGetTransaction() throws TransactionException {
      return null;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition)
        throws TransactionException {
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
    }
  }

}
