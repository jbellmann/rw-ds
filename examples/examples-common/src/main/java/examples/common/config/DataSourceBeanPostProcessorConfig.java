package examples.common.config;

import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.listener.SingleQueryCountHolder;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceBeanPostProcessorConfig {

  @Slf4j
  @Configuration
  static class WriteDataSourceBeanPostProcessorConfig {

    @Bean
    public static BeanPostProcessor writeDataSourceBeanPostProcessor(
        WriteDataSourceExecutionListener writeDataSourceExecutionListener) {
      log.info("Create BeanPostProcessor for writeDataSource ...");
      return new DataSourceBeanPostProcessor("WRITE_DATASOURCE", "rwdsWrite", writeDataSourceExecutionListener.getListeners());
    }

  }

  @Slf4j
  @Configuration
  static class ReadDataSourceBeanPostProcessorConfig {

    @Bean
    public static BeanPostProcessor readDataSourceBeanPostProcessor(
        ReadDataSourceExecutionListener readDataSourceExecutionListener) {
      log.info("Create BeanPostProcessor for readDataSource ...");
      return new DataSourceBeanPostProcessor("READ_DATASOURCE", "rwdsRead", readDataSourceExecutionListener.getListeners());
    }
  }

  @Slf4j
  @RequiredArgsConstructor
  static class DataSourceBeanPostProcessor implements BeanPostProcessor {

    private final String name;
    private final String prefix;

    private final List<QueryExecutionListener> listener;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
      if(!(bean instanceof DataSource)) {
        return bean;
      }

      if(! beanName.startsWith(prefix)) {
        return bean;
      }

      log.info("PostProcessing DataSource with beanName : {}", beanName);

      ChainListener chainListener = new ChainListener();
      chainListener.setListeners(listener);

      return ProxyDataSourceBuilder
          .create((DataSource) bean)
          .name(name)
          .listener(chainListener)
          .build();
    }
  }

  @Configuration
  static class ReadExecutionListenerConfig {

    @Bean(name = "readDataSourceQueryCountListener")
    public static DataSourceQueryCountListener readDataSourceQueryCountListener() {
      DataSourceQueryCountListener l =  new DataSourceQueryCountListener();
      l.setQueryCountStrategy(new SingleQueryCountHolder());
      return l;
    }

    @Bean(name = "writeDataSourceQueryCountListener")
    public static DataSourceQueryCountListener writeDataSourceQueryCountListener() {
      DataSourceQueryCountListener l =  new DataSourceQueryCountListener();
      l.setQueryCountStrategy(new SingleQueryCountHolder());
      return l;
    }

    @Bean
    public static WriteDataSourceExecutionListener writeDataSourceExecutionListener(
        @Qualifier("writeDataSourceQueryCountListener") DataSourceQueryCountListener dataSourceQueryCountListener) {
      return () -> List.of(
          createLoggingListener("WRITE_DATASOURCE"),
          dataSourceQueryCountListener
      );
    }

    @Bean
    public static ReadDataSourceExecutionListener readDataSourceExecutionListener(
        @Qualifier("readDataSourceQueryCountListener") DataSourceQueryCountListener dataSourceQueryCountListener) {
      return () -> List.of(
        createLoggingListener("READ_DATASOURCE"),
        dataSourceQueryCountListener
      );
    }
  }

  protected static SLF4JQueryLoggingListener createLoggingListener(String name) {
    SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
    loggingListener.setLogLevel(SLF4JLogLevel.INFO);
    loggingListener.setLogger(name);
    loggingListener.setWriteConnectionId(false);
    return loggingListener;
  }
}
