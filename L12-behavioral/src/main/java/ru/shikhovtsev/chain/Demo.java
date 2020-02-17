package ru.shikhovtsev.chain;

import ru.shikhovtsev.chain.middleware.Middleware;
import ru.shikhovtsev.chain.middleware.RoleCheckMiddleware;
import ru.shikhovtsev.chain.middleware.ThrottlingMiddleware;
import ru.shikhovtsev.chain.middleware.UserExistsMiddleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Demo {
  private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Server server;

  private static void init() {
    server = new Server();
    server.register("admin@example.com", "admin_pass");
    server.register("user@example.com", "user_pass");

    // All checks are linked. Client can build various chains using the same
    // components.
    Middleware middleware = new ThrottlingMiddleware(2);
    middleware.setNext(new UserExistsMiddleware(server))
        .setNext(new RoleCheckMiddleware());

    // Server gets a chain from client code.
    server.setMiddleware(middleware);
  }

  /*public static void main(String[] args) throws IOException {
    init();

    boolean success;
    do {
      System.out.print("Enter email: ");
      String email = reader.readLine();
      System.out.print("Input password: ");
      String password = reader.readLine();
      success = server.logIn(email, password);
    } while (!success);
  }*/

  public static void main(String[] args) {
    var pattern = Pattern.compile("^[0-9]{3}-[0-9]{2}(|-[0-9])");
    var matcher = pattern.matcher("000-44");
    System.out.println(matcher.find());
    matcher = pattern.matcher("000-44-4");
    System.out.println(matcher.find());
    System.out.println(matcher.matches());
  }
}
