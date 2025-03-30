package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.OrdersMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AdminController {

    public static void routes(Javalin app) {
        app.get("/admin/orders", AdminController::displayOrders);
    }

    private static void displayOrders(Context ctx) throws DatabaseException {
        User user = ctx.sessionAttribute("user");

        List<Order> orders = OrdersMapper.getOrders();
        if (user != null && user.getRole().equals("admin")) {
            ctx.attribute("email", user.getEmail());
            ctx.attribute("orders", orders);
            ctx.render("admin.html");
        }
        else {
            ctx.status(401).result("Authentication failed");
        }
    }
}
