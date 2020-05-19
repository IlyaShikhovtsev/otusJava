package ru.shikhovtsev.web.servlet;


import ru.shikhovtsev.core.model.Role;
import ru.shikhovtsev.core.service.DBServiceUser;
import ru.shikhovtsev.web.Context;
import ru.shikhovtsev.web.template.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginServlet extends HttpServlet {

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";
  private static final int MAX_INACTIVE_INTERVAL = 30;
  private static final String LOGIN_PAGE_TEMPLATE = "login.html";


  private final DBServiceUser serviceUser;
  private final TemplateProcessor templateProcessor;

  public LoginServlet() {
    this.serviceUser = Context.getService(DBServiceUser.class);
    this.templateProcessor = Context.getService(TemplateProcessor.class);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, Collections.emptyMap()));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);

    if (authenticate(login, password)) {
      HttpSession session = request.getSession();
      session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
      response.sendRedirect("/users");
    } else {
      response.setStatus(SC_UNAUTHORIZED);
      response.getWriter().println("Wrong login or password, or you are not ADMIN");
    }
  }

  private boolean authenticate(String login, String password) {
    return serviceUser.getUserByLogin(login)
        .map(u -> u.getPassword().equals(password) && u.getRole().equals(Role.ADMIN))
        .orElse(false);
  }

}
