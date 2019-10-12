package ru.shikhovtsev;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public class MyArrayLong implements AutoCloseable {

  private final Unsafe unsafe;
  private final int elementSizeBytes;
  private long size;
  private long arrayBeginIdx;

  MyArrayLong(int size) throws Exception {
    this.size = size;

    Constructor<Unsafe> declaredConstructor = Unsafe.class.getDeclaredConstructor();
    declaredConstructor.setAccessible(true);
    this.unsafe = declaredConstructor.newInstance();

    this.elementSizeBytes = Long.SIZE / 8;
    arrayBeginIdx = unsafe.allocateMemory(this.size * elementSizeBytes);
  }

  void setValue(long index, long value) {
    if (index == size) {
      size *= 2;
      arrayBeginIdx = unsafe.reallocateMemory(arrayBeginIdx, size * elementSizeBytes);
    }
    unsafe.putLong(offset(index), value);
  }

  private long offset(long index) {
    return arrayBeginIdx + elementSizeBytes * index;
  }

  long getValue(long index) {
    return unsafe.getLong(offset(index));
  }

  @Override
  public void close() {
    unsafe.freeMemory(arrayBeginIdx);
  }
}
