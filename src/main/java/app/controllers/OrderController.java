package app.controllers;

import app.entities.Bottom;
import app.entities.Order;
import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.models.User;
import app.persistence.BottomsMapper;
import app.persistence.OrdersMapper;
import app.persistence.ToppingsMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class OrderController {

    public static void routes(Javalin app) {
        app.get("/", OrderController::displayFrontpage);
        app.post("/order/create", OrderController::createOrder);
    }

    private static void displayFrontpage(Context ctx) throws DatabaseException {
        getBottoms(ctx);
        getToppings(ctx);
        getAmount(ctx);
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.attribute("email", "");
            return;
        }
        ctx.attribute("email", user.getEmail());
        ctx.render("index.html");
    }

    private static void createOrder(Context ctx) throws DatabaseException {
        String bottom = ctx.formParam("bottom");
        String topping = ctx.formParam("topping");
        int amount = Integer.parseInt(ctx.formParam("amount"));

        User user = ctx.sessionAttribute("user");
        int userId = user.getId();
        Order order = new Order(userId, topping, bottom, amount, false);
        OrdersMapper.createOrder(order);
        ctx.status(200);
        displayFrontpage(ctx);
    }

    private static void getBottoms(Context ctx) throws DatabaseException {
        List<Bottom> bottoms = BottomsMapper.getBottoms();
        ctx.attribute("bottomTypes", bottoms);
    }

    private static void getToppings(Context ctx) throws DatabaseException {
        List<Topping> toppings = ToppingsMapper.getToppings();
        ctx.attribute("toppingTypes", toppings);
    }

    private static void getAmount(Context ctx) {
        int amountLimit = 100;
        int[] amount = new int[amountLimit];
        for (int i = 0; i < amount.length; i++) {
            amount[i] = i + 1;
        }
        ctx.attribute("amounts", amount);
    }
}