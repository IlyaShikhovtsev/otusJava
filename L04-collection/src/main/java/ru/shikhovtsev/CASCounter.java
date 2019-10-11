package ru.shikhovtsev;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CASCounter {

  private Unsafe unsafe;
  private volatile long counter = 0;
  private long offset;

  private Unsafe getUnsafe() throws Exception {
    Constructor<Unsafe> unsafe = Unsafe.class.getDeclaredConstructor();
    unsafe.setAccessible(true);
    return unsafe.newInstance();
  }

  private CASCounter() throws Exception {
    unsafe = getUnsafe();
    offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
  }

  private void increment() {
    long before = counter;
    while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
      counter = before;
    }
  }

  public static void main(String[] args) throws Exception {
    int NUM_OF_THREADS = 1_000;
    int NUM_OF_INCREMENTS = 10_000;
    ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
    CASCounter casCounter = new CASCounter();

    IntStream.rangeClosed(0, NUM_OF_THREADS - 1)
        .forEach(i -> service.submit(() -> IntStream
            .rangeClosed(0, NUM_OF_INCREMENTS - 1)
            .forEach(j -> casCounter.increment())));
  }
}
