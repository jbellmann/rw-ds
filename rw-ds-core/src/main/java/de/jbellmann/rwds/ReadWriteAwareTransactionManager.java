package de.jbellmann.rwds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

@Slf4j
public class ReadWriteAwareTransactionManager implements PlatformTransactionManager {

  private final PlatformTransactionManager delegate;

  public ReadWriteAwareTransactionManager(PlatformTransactionManager delegate) {
    this.delegate = delegate;
  }

  @Override
  public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
    ReadWriteDataSource.setReadOnlyDataSource(definition.isReadOnly());
    log.debug("Set by transaction : {}", definition.getName() != null ? definition.getName() : "UNKNOWN");
    return delegate.getTransaction(definition);
  }

  @Override
  public void commit(TransactionStatus status) throws TransactionException {
    log.trace("Committing transaction : {}", status);
    delegate.commit(status);
  }

  @Override
  public void rollback(TransactionStatus status) throws TransactionException {
    log.trace("Rollback transaction : {}", status);
    delegate.rollback(status);
  }
}
