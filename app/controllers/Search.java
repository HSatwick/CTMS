package controllers;

/**
 * Created by user on 11/30/2015.
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

public class Search extends Controller {

    public Result search(){

        DynamicForm dataForm = form().bindFromRequest();
        String searchField = dataForm.data().get("search");
        String bor = dataForm.data().get("borough");
        String cat = dataForm.data().get("categories");

        if(searchField.trim().length()==0 && bor.equals("") && cat.equals("")){
            List<ToolCategory> toolCar = ToolCategory.find.all();
            List<Borough> boroughs = Borough.borough.all();
            List<Tools> tool = Tools.find_tools.where().eq("available",1).findList();
            return ok(index.render("",toolCar,boroughs,display.render(tool)));
        }

        return ok();
    }

}
