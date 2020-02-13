package ru.shikhovtsev.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestsRunner {

  private TestsRunner() {
  }

  public static void runTests(String className) {
    Class<?> testClass;
    try {
      testClass = Class.forName(className);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    var processor = new AnnotationProcessor(testClass);

    var errors = new ArrayList<>();
    var passed = new ArrayList<>();

    for (Method testMethod : processor.getTestMethods()) {
      System.out.println("Test method - " + testMethod.getName() + "\n----------------------------------");

      boolean failed = false;
      Object o = null;
      try {
        o = testClass.getDeclaredConstructor().newInstance();
      } catch (Exception ignored) {
      }

      try {
        for (Method method : processor.getBeforeEachMethods()) {
          method.invoke(o);
        }

        testMethod.invoke(o);
      } catch (Exception e) {
        failed = true;
      }

      failed |= executeEachAfterMethods(o, processor);

      if (failed) {
        errors.add(testMethod.getName());
        System.out.println("----------------------------------\n" + testMethod.getName() + " failed! \n\n");
      } else {
        passed.add(testMethod.getName());
        System.out.println("----------------------------------\n" + testMethod.getName() + " passed \n\n");
      }
    }


    System.out.println("Number of all tests = " + processor.getTestMethods().size());
    System.out.println("Passed tests - " + passed);
    System.out.println("Failed tests - " + errors);
  }

  private static boolean executeEachAfterMethods(Object o, AnnotationProcessor processor) {
    boolean failed = false;
    for (Method method : processor.getAfterEachMethods()) {
      try {
        method.invoke(o);
      } catch (Exception ignored) {
        failed = true;
      }
    }
    return failed;
  }
}
