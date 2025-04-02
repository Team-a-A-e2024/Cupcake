package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.util.SessionUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class HomeController {

    public static void routes(Javalin app) {
        app.get("/", HomeController::displayFrontpage);
    }

    public static void displayFrontpage(Context ctx) throws DatabaseException {
        OrderController.getBottoms(ctx);
        OrderController.getToppings(ctx);
        OrderController.getAmount(ctx);
        User user = SessionUtil.UpdateUser(ctx.sessionAttribute("user"));

        if (user != null) {
            ctx.attribute("email", user.getEmail());
            ctx.attribute("role", user.getRole());
        }

        ctx.render("index.html");
    }
}
