package controllers;

/**
 * Created by user on 11/27/2015.
 */

import models.*;
import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.DynamicForm;
import java.util.*;
import org.joda.time.*;


import views.html.*;


import static play.data.Form.form;

import static play.libs.Json.toJson;


public class Comments extends Controller{

    public Result getCommentUI(Long id){
        Tools t = Tools.find_tools.where().eq("tool_id",id).findUnique();
        return ok(comment.render(id,t,Search.search,Search.borough,Search.categories));
    }

    public Result addComment(Long id){
        System.out.println(id);
        Tools t = Tools.find_tools.where().eq("tool_id",id).findUnique();
        Users u = Users.find.where().eq("user_id", Long.parseLong(session("user_id"))).findUnique();
        DynamicForm dataForm = form().bindFromRequest();
        String desc = dataForm.data().get("comment_body");
        Comment com = new Comment();
        com.body = desc;
        com.tool = t;
        com.user = u;
        com.datetime_posted = new DateTime();
        com.save();
        return redirect(routes.SeeMore.displayMore(id));
    }

}
