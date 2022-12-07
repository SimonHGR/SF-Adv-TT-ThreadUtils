package atomics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
//  public static /*volatile*/ long count = 0;

//  private static Object rendezvous = new Object();
//  private static ReentrantLock rl = new ReentrantLock();
//  private static AtomicLong count = new AtomicLong();
//  private static LongAdder count = new LongAdder();
  private static LongAccumulator count =
    new LongAccumulator((a, b) -> a + b, 0);

  public static void main(String[] args) throws Throwable {
    Runnable r = () -> {
      for (int i = 0; i < 10_000; i++) {
        count.accumulate(1);

//        count.increment();

//        count.incrementAndGet();

//        synchronized (rendezvous) {
//        rl.lock();
//        try {
//          count++; // read-modify-write
//        } finally {
//          rl.unlock();
//        }
      }
    };

    System.out.println("count before " + count);

    long start = System.nanoTime();

    List<Thread> lt = new ArrayList<>();
    for (int i = 0; i < 100_000; i++) {
      Thread t = new Thread(r);
      lt.add(t);
      t.start();
    }

    for (Thread t : lt) {
      t.join();
    }
    long time = System.nanoTime() - start;
//    System.out.println("count after " + count);
    System.out.println("count after " + count.longValue());
    System.out.println("time was " + (time / 1_000_000_000.0));
  }
}
