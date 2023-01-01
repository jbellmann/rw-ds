package example.support.test;

import lombok.Value;

@Value
public class ClientRunnerResult {
  private final int readExecutionCount;
  private final int writeExecutionCount;

}
