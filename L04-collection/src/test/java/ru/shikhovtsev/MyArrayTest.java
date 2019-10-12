package ru.shikhovtsev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyArrayTest {

  @Test
  void setGetValue() throws Exception {
    MyArrayLong myArr = new MyArrayLong(1);
    int val = 45;
    int idx = 0;
    myArr.setValue(idx, val);
    assertEquals(val, myArr.getValue(idx), "value from array");
  }

  @Test
  void setGetValueSeq() throws Exception {
    int size = 100;
    MyArrayLong myArr = new MyArrayLong(size);
    for (int idx = 0; idx < size; idx++) {
      myArr.setValue(idx, idx);
    }

    for (int idx = 0; idx < size; idx++) {
      assertEquals(idx, myArr.getValue(idx), "value from array for idx:" + idx);
    }
  }


  @Test
  void incSize() throws Exception {
    int sizeInit = 1;
    int sizeMax = 100;
    MyArrayLong myArr = new MyArrayLong(sizeInit);
    for (int idx = 0; idx < sizeMax; idx++) {
      myArr.setValue(idx, idx);
    }

    for (int idx = 0; idx < sizeMax; idx++) {
      assertEquals(idx, myArr.getValue(idx), "value from array for idx:" + idx);
    }
  }

}
