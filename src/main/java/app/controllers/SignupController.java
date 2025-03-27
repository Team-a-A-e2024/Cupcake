package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import app.persistence.*;

public class SignupController {

    public static void routes(Javalin app) {
        app.get("/signup", ctx ->  SignupController.SignupGet(ctx));
        app.post("/signup", ctx -> SignupController.SignupPost(ctx));
    }

    public static void SignupGet(@NotNull Context ctx) {
        ctx.render("createUser.html");
    }

    public static void SignupPost(@NotNull Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        //todo: default value for credit
        User user = new User(email,password,null,200);
        try{
            user = UsersMapper.addUserByObject(user);
            ctx.sessionAttribute("user", user);
            ctx.redirect("/");
        }
        catch (DatabaseException ex){
            ex.printStackTrace();
            ctx.attribute("error","signup failed");
            ctx.render("createUser.html");
        }
    }
}