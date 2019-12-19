package ru.shikhovtsev.proxy;

import ru.shikhovtsev.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IoCContainer {

  @SuppressWarnings("unchecked")
  public <U, T extends U> U getProxiedObject(Class<U> castTo, T obj) {
    if (!castTo.isInterface()) {
      throw new RuntimeException("The first parameter must be an interface!");
    }

    if (Arrays.stream(obj.getClass().getMethods()).anyMatch(m -> m.isAnnotationPresent(Log.class))) {
      var invocationHandler = new MyInvocationHandler(obj);

      return (U) Proxy.newProxyInstance(
          this.getClass().getClassLoader(), obj.getClass().getInterfaces(),
          invocationHandler);
    }
    return obj;
  }

  private static class MyInvocationHandler implements InvocationHandler {

    private Object obj;
    private List<Method> methodsWithLog;

    public MyInvocationHandler(Object obj) {
      this.obj = obj;
      methodsWithLog = Arrays.stream(obj.getClass().getMethods())
          .filter(m -> m.isAnnotationPresent(Log.class))
          .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (methodsWithLog.stream().anyMatch(m -> m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))) {
        System.out.println("Invoking method: " + method.getName() + ", params: " + Arrays.toString(method.getParameters()));
      }
      return method.invoke(obj, args);
    }
  }

}
