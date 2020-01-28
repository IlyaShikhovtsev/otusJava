package ru.shikhovtsev.framework;

import ru.shikhovtsev.framework.annotations.AfterEach;
import ru.shikhovtsev.framework.annotations.BeforeEach;
import ru.shikhovtsev.framework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestsRunner {

  public static void main(String[] args) throws Exception {
    var testClass = Class.forName(args[0]);

    Map<Class<? extends Annotation>, List<Method>> annotatedMethods = new HashMap<>();
    annotatedMethods.put(Test.class, new ArrayList<>());
    annotatedMethods.put(AfterEach.class, new ArrayList<>());
    annotatedMethods.put(BeforeEach.class, new ArrayList<>());

    var methods = testClass.getMethods();
    for (Method method : methods) {
      for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
        Optional.ofNullable(annotatedMethods.get(declaredAnnotation.annotationType())).ifPresent(l -> l.add(method));
      }
    }

    var errors = new ArrayList<>();
    var passed = new ArrayList<>();
    Object o;
    try {
      o = testClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      System.out.println("Exception while instantiating an object");
      return;
    }

    for (Method testMethod : annotatedMethods.get(Test.class)) {
      System.out.println("Test method - " + testMethod.getName() + "\n----------------------------------");
      try {
        for (Method method : annotatedMethods.get(BeforeEach.class)) {
          method.invoke(o);
        }

        testMethod.invoke(o);

        for (Method method : annotatedMethods.get(AfterEach.class)) {
          method.invoke(o);
        }

        passed.add(testMethod.getName());
        System.out.println("----------------------------------\n" + testMethod.getName() + " passed \n\n");
      } catch (Exception e) {
        errors.add(testMethod.getName());
        System.out.println("----------------------------------\n" + testMethod.getName() + " failed! \n\n");
      }
    }

    System.out.println("Number of all tests = " + annotatedMethods.get(Test.class).size());
    System.out.println("Passed tests - " + passed);
    System.out.println("Failed tests - " + errors);
  }
}
