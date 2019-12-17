package ru.shikhovtsev.proxy;

import ru.shikhovtsev.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class IoCContainer {

  public MyService getService() {
    if (Arrays.stream(MyServiceImpl.class.getMethods()).anyMatch(m -> m.isAnnotationPresent(Log.class))) {
      var invocationHandler = new MyInvocationHandler(new MyServiceImpl());
      return (MyService) Proxy.newProxyInstance(
          this.getClass().getClassLoader(), new Class<?>[]{MyService.class},
          invocationHandler);
    }
    return new MyServiceImpl();
  }

  private static class MyInvocationHandler implements InvocationHandler {

    private MyService myService;

    public MyInvocationHandler(MyService myService) {
      this.myService = myService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      var methodOfImpl = myService.getClass().getMethod(method.getName(), method.getParameterTypes());
      if (methodOfImpl.isAnnotationPresent(Log.class)) {
        System.out.println("Invoking method: " + method.getName() + ", params: " + Arrays.toString(method.getParameters()));
      }
      return method.invoke(myService, args);
    }
  }

}
