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
        return redirect(routes.Application.index());
    }

    public Result setUpload(){
        List<ToolCategory> tool = ToolCategory.find.all();
        List<Borough> borough = Borough.borough.all();
        return ok(upload.render(tool,borough));
    }


}
