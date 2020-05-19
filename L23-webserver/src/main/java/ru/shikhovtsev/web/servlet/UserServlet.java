package ru.shikhovtsev.web.servlet;

import lombok.extern.slf4j.Slf4j;
import ru.shikhovtsev.core.model.Address;
import ru.shikhovtsev.core.model.Phone;
import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.model.User;
import ru.shikhovtsev.core.service.DBServiceUser;
import ru.shikhovtsev.web.Context;
import ru.shikhovtsev.web.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class UserServlet extends HttpServlet {

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";
  private static final String PARAM_NAME = "name";
  private static final String PARAM_ADDRESS = "address";
  private static final String PARAM_PHONE = "phone";

  private final DBServiceUser userService;
  private final String USER_PAGE_TEMPLATE = "users.ftl";

  public UserServlet() {
    this.userService = Context.getService(DBServiceUser.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    resp.setStatus(HttpServletResponse.SC_OK);

    var data = new HashMap<String, Object>();

    List<User> users = userService.getAll();
    data.put("users", users);

    resp.getWriter().println(Context.getService(TemplateProcessor.class).getPage(USER_PAGE_TEMPLATE, data));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);
    String name = request.getParameter(PARAM_NAME);
    String address = request.getParameter(PARAM_ADDRESS);
    String phone = request.getParameter(PARAM_PHONE);

    var newUser = new User(login, password, name, new Address(address), new Phone(phone), Role.USER);
    userService.createOrUpdate(newUser);

    response.sendRedirect("users");
  }
}
