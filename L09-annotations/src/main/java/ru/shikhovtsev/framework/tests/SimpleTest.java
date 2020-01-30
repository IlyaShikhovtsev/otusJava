package ru.shikhovtsev.framework.tests;

import ru.shikhovtsev.framework.annotations.AfterEach;
import ru.shikhovtsev.framework.annotations.BeforeEach;
import ru.shikhovtsev.framework.annotations.Test;

public class SimpleTest {

  @Test
  public void test() {
    System.out.println("First test!");
  }

  @Test
  public void test1() {
    System.out.println("Exception");
    throw new RuntimeException();
  }

  @Test
  public void checkAfterEachIfTestFailed() {
    System.out.println("check afterEach");
    throw new RuntimeException();
  }

  @Test
  public void test3() {
    System.out.println("First test!");
  }


  @BeforeEach
  public void before() {
    System.out.println("Before");
  }

  @AfterEach
  public void firstAfter() {
    System.out.println("first After");
  }

  @AfterEach
  public void secondAfter() {
    System.out.println("second After");
  }

}
