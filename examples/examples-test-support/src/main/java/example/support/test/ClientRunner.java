package example.support.test;

import example.support.client.TestClient;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;

public class ClientRunner {

  private static final Random random = new Random(System.currentTimeMillis());

  @SneakyThrows
  public ClientRunnerResult run(int port) {
    TestClient client = new TestClient(port);
    int writeRangeEnd = random.nextInt(5, 34);
    int readRangeEnd = random.nextInt(5, 34);
    AtomicInteger writeActionCounter = new AtomicInteger();
    AtomicInteger readActionCounter = new AtomicInteger();
    IntStream.range(0, writeRangeEnd).forEach(i -> {
      writeActionCounter.incrementAndGet();
      client.createStore();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Thread.sleep(1_000);
    IntStream.range(0, readRangeEnd).forEach(i -> {
      readActionCounter.incrementAndGet();
      client.readStores();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });
    return new ClientRunnerResult(readActionCounter.get(), writeActionCounter.get());
  }

  @SneakyThrows
  public ClientRunnerResult runRandomly(int port, int numberOfRequest) {
    TestClient client = new TestClient(port);
    AtomicInteger writeActionCounter = new AtomicInteger();
    AtomicInteger readActionCounter = new AtomicInteger();
    AtomicInteger requestCounter = new AtomicInteger();
    while(requestCounter.get() < numberOfRequest) {
      boolean next = random.nextBoolean();
      if(next) {
        client.readStores();
        readActionCounter.incrementAndGet();
        requestCounter.incrementAndGet();
      } else {
        client.createStore();
        writeActionCounter.incrementAndGet();
        requestCounter.incrementAndGet();
      }
    }
    return new ClientRunnerResult(readActionCounter.get(), writeActionCounter.get());
  }
}
