package de.jbellmann.rwds;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.PlatformTransactionManager;

class TransactionManagerBeanPostProcessorTest {

  @Test
  void testBeanPostProcessor() {
    TransactionManagerBeanPostProcessor postProcessor = new TransactionManagerBeanPostProcessor();

    Object processedBean = postProcessor.postProcessAfterInitialization("STRING", "STRING_TEST_BEAN");
    Assertions.assertSame("STRING", processedBean);
    Assertions.assertInstanceOf(String.class, processedBean);
    Assertions.assertFalse(processedBean instanceof ReadWriteAwareTransactionManager);

    processedBean = postProcessor.postProcessAfterInitialization(23L, "LONG_TEST_BEAN");
    Assertions.assertSame(23L, processedBean);
    Assertions.assertInstanceOf(Long.class, processedBean);
    Assertions.assertFalse(processedBean instanceof ReadWriteAwareTransactionManager);

    PlatformTransactionManager mockedTransactionManager = Mockito.mock(PlatformTransactionManager.class);
    processedBean = postProcessor.postProcessAfterInitialization(mockedTransactionManager, "jpaTransactionManager");
    Assertions.assertNotSame(mockedTransactionManager, processedBean);
    Assertions.assertInstanceOf(ReadWriteAwareTransactionManager.class, processedBean);
  }

}
