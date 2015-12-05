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

    public static String search = "";
    public static String borough = "";
    public static String categories = "";

    public Result search(){

        DynamicForm dataForm = form().bindFromRequest();
        search = dataForm.data().get("search");
        borough = dataForm.data().get("borough");
        categories = dataForm.data().get("categories");

        List<ToolCategory> toolCar = ToolCategory.find.all();
        List<Borough> boroughs = Borough.borough.all();

        if(borough.equals("") && categories.equals("")){
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%").findList();
            System.out.println(tool);
            return ok(index.render("",toolCar,boroughs,display.render(tool)));
        }else if (!borough.equals("") && categories.equals("")){
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("borough_bor_id", Integer.parseInt(borough)).findList();
            return ok(index.render("",toolCar,boroughs,display.render(tool)));
        }else if (borough.equals("") && !categories.equals("")) {
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("tool_type_cat_id", Integer.parseInt(categories)).findList();
            return ok(index.render("", toolCar, boroughs, display.render(tool)));
        }else {
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("tool_type_cat_id", Integer.parseInt(categories))
                    .eq("borough_bor_id", Integer.parseInt(borough)).findList();
            return ok(index.render("", toolCar, boroughs, display.render(tool)));
        }
    }
}
