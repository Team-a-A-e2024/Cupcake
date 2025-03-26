package app.controller;

import app.models.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class LoginController {

    private static ConnectionPool connectionPool;

    public LoginController(ConnectionPool pool) {
        connectionPool = pool;
    }

    public static void routes(Javalin app) {
        app.get("/login", LoginController::showLoginPage);
        app.post("/login", LoginController::handleLogin);
        app.get("/logout", LoginController::logout);
    }

    public static void showLoginPage(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user != null) {
            ctx.attribute("email", user.getEmail());
        }
        ctx.render("login.html");
    }

    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = UserMapper.getUserByEmailAndPassword(email, password, connectionPool);

        if (user != null) {
            ctx.sessionAttribute("user", user);
            ctx.redirect("/login");
        } else {
            ctx.attribute("error", "Invalid email or password");
            ctx.render("login.html");
        }
    }

    public static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }
}
