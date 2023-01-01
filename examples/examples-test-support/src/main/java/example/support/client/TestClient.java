package example.support.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class TestClient {

  private final RestTemplate rest = new RestTemplate();

  public TestClient(int port) {
    this.port = port;
    this.createUri = String.format("http://localhost:%s/api/create",port);
    this.storesUri = String.format("http://localhost:%s/api/stores",port);
  }

  private final int port;
  private final String createUri;
  private final String storesUri;

  public void createStore() {
    ResponseEntity<String> response = rest.getForEntity(createUri, String.class);
    log.info("RESPONSE_CREATE_STORE: \n{}", response.getBody());
  }

  public void readStores() {
    ResponseEntity<String> response = rest.getForEntity(storesUri, String.class);
    log.info("RESPONSE_READ_STORES: \n{}", response.getBody());
  }

}
