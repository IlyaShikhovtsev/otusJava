package ru.shikhovtsev.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.shikhovtsev.core.service.DbServiceUserImpl;
import ru.shikhovtsev.web.helpers.FileSystemHelper;
import ru.shikhovtsev.web.servlet.AuthorizationFilter;
import ru.shikhovtsev.web.servlet.LoginServlet;
import ru.shikhovtsev.web.servlet.UserServlet;
import ru.shikhovtsev.web.template.TemplateProcessorImpl;

public class WebServer {
  private static final String START_PAGE_NAME = "index.html";
  private static final String COMMON_RESOURCES_DIR = "static";
  private final Server server;
  private final DbServiceUserImpl userService;
  private final TemplateProcessorImpl templateProcessor;

  public WebServer(DbServiceUserImpl userService, TemplateProcessorImpl templateProcessor) {
    this.userService = userService;
    this.templateProcessor = templateProcessor;

    server = new Server(8080);

    var handlers = new HandlerList();
    ServletContextHandler servletHandler = createServletHandler();
    handlers.addHandler(createResourceHandler());
    handlers.addHandler(applySecurity(servletHandler));

    server.setHandler(handlers);
  }

  public void start() throws Exception {
    server.start();
  }

  public void join() throws InterruptedException {
    server.join();
  }

  private ServletContextHandler createServletHandler() {
    var handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.addServlet(new ServletHolder(new UserServlet(userService, templateProcessor)), "/users");
    return handler;
  }

  private ResourceHandler createResourceHandler() {
    var resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(false);
    resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
    resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
    return resourceHandler;
  }

  private Handler applySecurity(ServletContextHandler servletHandler) {
    servletHandler.addServlet(new ServletHolder(new LoginServlet(userService, templateProcessor)), "/login");
    AuthorizationFilter authorizationFilter = new AuthorizationFilter();
    servletHandler.addFilter(new FilterHolder(authorizationFilter), "/users", null);
    return servletHandler;
  }

  /*private Handler applySecurity(ServletContextHandler servletHandler) {
    Constraint constraint = new Constraint();
    constraint.setName("auth");
    constraint.setAuthenticate(true);
    constraint.setRoles(new String[]{"admin"});

    ConstraintMapping mapping = new ConstraintMapping();
    mapping.setPathSpec("/users");
    mapping.setConstraint(constraint);

    ConstraintSecurityHandler security = new ConstraintSecurityHandler();
    security.setAuthenticator(new BasicAuthenticator());
    security.setLoginService(new LoginService(Context.getService(DBServiceUser.class)));
    security.setConstraintMappings(Collections.singletonList(mapping));

    security.setHandler(new HandlerList(servletHandler));

    return security;
  }*/
}
