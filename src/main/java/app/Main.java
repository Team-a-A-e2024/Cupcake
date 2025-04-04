package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.*;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Mappers
        OrdersMapper.setConnectionPool(connectionPool);
        BottomsMapper.setConnectionPool(connectionPool);
        ToppingsMapper.setConnectionPool(connectionPool);
        UsersMapper.setConnectionPool(connectionPool);

        // Routing
        HomeController.routes(app);
        OrderController.routes(app);
        LoginController.routes(app);
        SignupController.routes(app);
        AdminController.routes(app);
    }
}