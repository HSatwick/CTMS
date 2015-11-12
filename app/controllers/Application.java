package controllers;

import models.Users;
import play.*;
import play.mvc.*;
import play.data.Form;
import java.util.*;

import views.html.*;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static String user = "";
    public static String status = "Sign In", newStatus = "Sign Out";

    public Result index() {
        return ok(index.render("Community Tool Management System",user, status));
    }

    public Result showUserForm() {
        return ok(user_login.render("Community Tool Management System"));
    }

    public Result addUser(){
        Users u = Form.form(Users.class).bindFromRequest().get();
        u.save();
        return redirect(routes.Application.index());
    }

    public Result signInUser(){
        List<Users> u = Users.find.all();
        System.out.println(u.size());
        return ok(toJson(u));
    }

}
