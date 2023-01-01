package examples.common.config;

import examples.common.AnotherStoreController;
import examples.common.AnotherStoreService;
import examples.common.StandardStoreController;
import examples.common.StandardStoreService;
import examples.common.StoreReader;
import examples.common.StoreWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class StoreAppConfig {

  @Bean
  StandardStoreService standardStoreService(StoreReader storeReader, StoreWriter storeWriter) {
    return new StandardStoreService(storeReader, storeWriter);
  }
  @Bean
  StandardStoreController storeController(StandardStoreService standardStoreService) {
    return new StandardStoreController(standardStoreService);
  }

  @Bean
  AnotherStoreService anotherStoreService(StoreReader storeReader, StoreWriter storeWriter) {
    return new AnotherStoreService(storeReader, storeWriter);
  }

  @Bean
  AnotherStoreController anotherStoreController(AnotherStoreService anotherStoreService) {
    return new AnotherStoreController(anotherStoreService);
  }
}
