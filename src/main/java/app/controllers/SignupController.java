package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.misc.StringToThymeleaf;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import app.persistence.*;

public class SignupController {

    public static void SignupGet(@NotNull Context ctx) {
        ctx.attribute("success",new StringToThymeleaf(""));
        ctx.render("createUser.html");
    }

    public static void SignupPost(@NotNull Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        //todo: default value for credit
        User user = new User(email,password,null,200);
        try{
            UserMapper.addUserByObject(connectionPool, user);
        }
        catch (DatabaseException ex){
            //todo: resolve
            ctx.attribute("success",new StringToThymeleaf("signup failed"));
            ctx.render("createUser.html");
        }
        ctx.attribute("success",new StringToThymeleaf("signup successful"));
        ctx.render("createUser.html");
    }
}
