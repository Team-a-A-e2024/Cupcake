package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.OrdersMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

public class AdminController {

    public static void routes(Javalin app) {
        app.get("/admin/orders", AdminController::displayOrders);
    }

    private static void displayOrders(Context ctx) throws DatabaseException {
        List<Order> orders = OrdersMapper.getOrders();
        ctx.attribute("orders", orders);
        ctx.status(200).result(orders.toString());
    }
}
