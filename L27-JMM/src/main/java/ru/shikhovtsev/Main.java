package ru.shikhovtsev;

public class Main {

  private static volatile boolean isFirst = true;

  public static void main(String[] args) {
    var thread1 = new Thread(() -> step(true, 5));
    var thread2 = new Thread(() -> step(false, 10));

    thread1.start();
    thread2.start();
  }

  private static void step(boolean b, int i) {
    try {
      int a = 1;
      boolean toTen = true;
      while (true) {
        Thread.sleep(i);

        if (b == isFirst) {

          if (toTen) {
            System.out.println(Thread.currentThread().getName() + " a=" + a++);
            if (a == 10) {
              toTen = false;
            }
          } else {
            System.out.println(Thread.currentThread().getName() + " a=" + a--);
            if (a == 1) {
              toTen = true;
            }
          }

          isFirst = !b;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
