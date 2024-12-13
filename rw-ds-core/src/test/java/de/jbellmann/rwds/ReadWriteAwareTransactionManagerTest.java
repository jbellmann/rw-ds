package de.jbellmann.rwds;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

class ReadWriteAwareTransactionManagerTest {

  @Test
  void testDelegation() {
    PlatformTransactionManager mockedDelegate = mock(PlatformTransactionManager.class);
    ReadWriteAwareTransactionManager transactionManager = new ReadWriteAwareTransactionManager(mockedDelegate);

    DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
    transactionDefinition.setName("TEST_TRANSACTION_DEFINITION");
    TransactionStatus transactionStatus = new DefaultTransactionStatus("TEST_TRANSACTION","TEST_TRANSACTION", true, true, false, false, true, "SUSPENDED_RESOURCES");

    transactionManager.getTransaction(transactionDefinition);
    transactionManager.commit(transactionStatus);
    transactionManager.rollback(transactionStatus);

    verify(mockedDelegate, Mockito.atMostOnce()).getTransaction(transactionDefinition);
    verify(mockedDelegate, Mockito.atMostOnce()).rollback(transactionStatus);
    verify(mockedDelegate, Mockito.atMostOnce()).commit(transactionStatus);
  }

}
