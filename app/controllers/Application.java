package controllers;

import models.*;
import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.DynamicForm;
import java.util.*;

import views.html.*;


import static play.data.Form.form;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static String user = "";
    public static String status = "Sign In", newStatus = "Logout";

    public Result index() {
        List<ToolCategory> tools = ToolCategory.find.all();
        List<Borough> boroughs = Borough.borough.all();
        return ok(index.render("Community Tool Management System",tools, boroughs));
    }

    public Result showUserForm() {
        return ok(user_login.render("Community Tool Management System"));
    }

    public Result signUp(){
        DynamicForm userForm = form().bindFromRequest();
        String firstName = userForm.data().get("first_name");
        String lastName = userForm.data().get("last_name");
        String username = userForm.data().get("emailAdrs");
        String password = userForm.data().get("password");

        Users user = Users.createNewUser(firstName, lastName, username, password);

        if(user == null) {
            flash("error", "Invalid user");
            return redirect(routes.Application.showUserForm());
        }else if(user != null){
            List<Users> u = Users.find.where().eq("emailAdrs", username).findList();
            if(u.size() != 0){
                flash("error", "email already exists");
                return redirect(routes.Application.showUserForm());
            }
        }

        user.save();

        flash("success", user.getName());
        session("user_id", user.getID());

        return redirect(routes.Application.index());
    }

    public Result logIn(){
        DynamicForm userForm = form().bindFromRequest();
        String username = userForm.data().get("emailAdrs");
        String password = userForm.data().get("password");

        Users user = Users.find.where().eq("emailAdrs", username).findUnique();

        if(user != null) {
            if(user.authenticate(user, password)) {
                session("user_id", user.getID());
                flash("success", "back " + user.getName());
            }else{
                flash("error", "Invalid password.");
                return redirect(routes.Application.showUserForm());
            }
        } else {
            flash("error", "Invalid username.");
            return redirect(routes.Application.showUserForm());
        }
        return redirect(routes.Application.index());
    }

    public Result logout() {
        session().remove("user_id");
        return redirect(routes.Application.index());
    }

}
