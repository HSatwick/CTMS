package controllers;

/**
 * Created by user on 11/24/2015.
 */

import models.*;
import play.*;
import play.mvc.*;
import play.data.Form;
import play.data.DynamicForm;
import java.util.*;

import views.html.*;


import static play.data.Form.form;

import static play.libs.Json.toJson;

public class Tool extends Controller {


    public Result upload(){
        DynamicForm userForm = form().bindFromRequest();
        String value = userForm.data().get("boroughs");
        String cat = userForm.data().get("categories");
        String name = userForm.data().get("toolName");
        String desc = userForm.data().get("toolDesc");
        ToolCategory toolCategory = ToolCategory.find.where().eq("cat_id", Integer.parseInt(cat)).findUnique();

        Borough borough = Borough.borough.where().eq("bor_id", Integer.parseInt(value)).findUnique();

        Users user = Users.find.where().eq("user_id", Long.parseLong(session("user_id"))).findUnique();

        Tools tool = Tools.uploadTool(name, desc, toolCategory, user, borough, 1);

        tool.save();
        return redirect(routes.Application.index());
    }

    public Result setUpload(){
        List<ToolCategory> tool = ToolCategory.find.all();
        List<Borough> borough = Borough.borough.all();
        return ok(upload.render(tool,borough));
    }


    public Result borrow(long toolid){
        Tools tool = Tools.find_tools.where().eq("tool_id", toolid).findUnique();
        tool.available = 0;
        Users borrower = Users.find.where().eq("user_id", Long.parseLong(session("user_id"))).findUnique();
        tool.tool_borrower = borrower;
        tool.update();

        Users user = Users.find.where().eq("user_id", Long.parseLong(session("user_id"))).findUnique();
        Borrowed borrow = Borrowed.borrowedTool(user, tool);
        borrow.save();
        return redirect(routes.Application.index());
    }

    public Result returnTool(long toolid){
        Tools tool = Tools.find_tools.where().eq("tool_id", toolid).findUnique();
        tool.available = 1;
        tool.tool_borrower = null;
        tool.update();

        return redirect(routes.Application.index());
    }
}
