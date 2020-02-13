package ru.shikhovtsev.framework;

import ru.shikhovtsev.framework.annotations.AfterEach;
import ru.shikhovtsev.framework.annotations.BeforeEach;
import ru.shikhovtsev.framework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationProcessor {

  private HashMap<Class<? extends Annotation>, List<Method>> annotatedMethods = new HashMap<>();

  public AnnotationProcessor(Class<?> testClass) {
    annotatedMethods.put(Test.class, new ArrayList<>());
    annotatedMethods.put(AfterEach.class, new ArrayList<>());
    annotatedMethods.put(BeforeEach.class, new ArrayList<>());

    var methods = testClass.getMethods();
    for (Method method : methods) {
      for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
        Optional.ofNullable(annotatedMethods.get(declaredAnnotation.annotationType())).ifPresent(l -> l.add(method));
      }
    }
  }

  public List<Method> getTestMethods() {
    return annotatedMethods.get(Test.class);
  }

  public List<Method> getBeforeEachMethods() {
    return annotatedMethods.get(BeforeEach.class);
  }

  public List<Method> getAfterEachMethods() {
    return annotatedMethods.get(AfterEach.class);
  }
}
