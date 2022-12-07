package atomics;

import java.util.ArrayList;
import java.util.List;

public class Counter {
  public static long count = 0;

  public static void main(String[] args) throws Throwable {
    Runnable r = () -> {
      for (int i = 0; i < 100_000; i++) {
          count++;
      }
    };

    System.out.println("count before " + count);

    long start = System.nanoTime();

    List<Thread> lt = new ArrayList<>();
    for (int i = 0; i < 10_000; i++) {
      Thread t = new Thread(r);
      lt.add(t);
      t.start();
    }

    for (Thread t : lt) {
      t.join();
    }
    long time = System.nanoTime() - start;
    System.out.println("count after " + count);
    System.out.println("time was " + (time / 1_000_000_000.0));
  }
}
