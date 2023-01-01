package de.jbellmann.rwds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
public class TransactionManagerBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if(!(bean instanceof PlatformTransactionManager)) {
      return bean;
    }

    if(bean instanceof ReadWriteAwareTransactionManager) {
      log.info("Bean is already of type {}, do not decorate bean", ReadWriteAwareTransactionManager.class.getSimpleName());
      return bean;
    }

    log.info("Got TransactionManager bean of type {}, will decorate it with {}", bean.getClass().getName(), ReadWriteAwareTransactionManager.class.getName());
    return new ReadWriteAwareTransactionManager((PlatformTransactionManager) bean);
  }
}
