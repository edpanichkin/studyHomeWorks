import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {
  public static final int THREAD_COUNT = 4;

  public static void main(String[] args) throws Exception {

    long start = System.currentTimeMillis();

    ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; i++) {
      CarNumbersGeneration carNumbersGeneration = new CarNumbersGeneration(i);
      executor.execute(carNumbersGeneration);
    }
    executor.shutdown();
    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

    System.out.println((System.currentTimeMillis() - start) + " ms");

  }
}