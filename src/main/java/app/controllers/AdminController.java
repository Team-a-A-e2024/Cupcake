package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.OrdersMapper;
import app.persistence.UsersMapper;
import app.util.SessionUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class AdminController {

    public static void routes(Javalin app) {
        app.get("/admin/orders", AdminController::displayOrders);
        app.get("/admin/customers", AdminController::displayCustomers);
    }

    private static void displayOrders(Context ctx) throws DatabaseException {
        User user = SessionUtil.UpdateUser(ctx.sessionAttribute("user"));

        List<Order> orders = OrdersMapper.getOrders();
        if (user != null && user.getRole().equals("admin")) {
            ctx.attribute("email", user.getEmail());
            ctx.attribute("role", user.getRole());
            ctx.attribute("orders", orders);
            ctx.render("admin.html");
        }
        else {
            ctx.status(401).result("Authentication failed");
        }
    }

    private static void displayCustomers(Context ctx) throws DatabaseException {
        User user = SessionUtil.UpdateUser(ctx.sessionAttribute("user"));

        List<User> users = UsersMapper.getUsersAndOrders();
        if (user != null && user.getRole().equals("admin")) {
            ctx.attribute("email", user.getEmail());
            ctx.attribute("role", user.getRole());
            ctx.attribute("users", users);
            ctx.render("customers.html");
        }
        else {
            ctx.status(401).result("Authentication failed");
        }
    }
}
