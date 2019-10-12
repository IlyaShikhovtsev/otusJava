package ru.shikhovtsev;

import java.util.ArrayList;
import java.util.List;

public class MyArrayDemo {

  public static void main(String[] args) throws Exception {

    int arraySizeMax = 1_000_000;
    int arraySizeInit = 10;
///////////
    long summ1 = 0;
    try (MyArrayLong myArr = new MyArrayLong(arraySizeInit)) {
      long begin = System.currentTimeMillis();

      for (int idx = 0; idx < arraySizeMax; idx++) {
        myArr.setValue(idx, idx);
      }

      for (int idx = 0; idx < arraySizeMax; idx++) {
        summ1 += myArr.getValue(idx);
      }
      System.out.println("myArr time:" + (System.currentTimeMillis() - begin));
    }
////
    System.out.println("-----");
    long summ2 = 0;
    List<Long> arrayList = new ArrayList<>(arraySizeInit);
    long begin = System.currentTimeMillis();

    for (long idx = 0; idx < arraySizeMax; idx++) {
      arrayList.add((int) idx, idx);
    }

    for (int idx = 0; idx < arraySizeMax; idx++) {
      summ2 += arrayList.get(idx);
    }
    System.out.println("ArrayList time:" + (System.currentTimeMillis() - begin));
    System.out.println("summ1:" + summ1 + ", summ2:" + summ2);
  }
}
