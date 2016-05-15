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

    //***************************************************************
    public static HashMap<Integer, Request_Tool> incoming(List<Request_Tool> t){
        HashMap<Integer, Request_Tool> tools = new HashMap<Integer, Request_Tool>();
        int j=0;
        for(int i=0; i<t.size(); i++){
            if(t.get(i).tools.available == 1){
                tools.put(j, t.get(i));
                j++;
            }
        }
        return tools;
    }

    public static HashMap<Integer, Request_Tool> outgoing(List<Request_Tool> r){
        HashMap<Integer, Request_Tool> tools = new HashMap<Integer, Request_Tool>();
        int j=0;
        for(int i=0; i<r.size(); i++){
            if(r.get(i).status == 0){
                tools.put(j, r.get(i));
                j++;
            }
        }
        for(int i=0; i<r.size(); i++){
            if(r.get(i).status != 0){
                tools.put(j, r.get(i));
                j++;
            }
        }
        return tools;
    }

    //********************************************************************

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
            HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
            HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
            return ok(index.render("",toolCar,boroughs,tools_borough, tools_inCat, display.render(tool)));
        }else if (!borough.equals("") && categories.equals("")){
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("borough_bor_id", Integer.parseInt(borough)).findList();
            HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
            HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
            return ok(index.render("",toolCar,boroughs,tools_borough, tools_inCat, display.render(tool)));
        }else if (borough.equals("") && !categories.equals("")) {
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("tool_type_cat_id", Integer.parseInt(categories)).findList();
            HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
            HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
            return ok(index.render("",toolCar,boroughs,tools_borough, tools_inCat, display.render(tool)));
        }else {
            List<Tools> tool = Tools.find_tools.where().eq("available", 1)
                    .ilike("tool_name", "%" + search + "%")
                    .eq("tool_type_cat_id", Integer.parseInt(categories))
                    .eq("borough_bor_id", Integer.parseInt(borough)).findList();
            HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
            HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
            return ok(index.render("",toolCar,boroughs,tools_borough, tools_inCat, display.render(tool)));
        }
    }

    public Result inventory(){
        List<ToolCategory> toolCar = ToolCategory.find.all();
        List<Borough> boroughs = Borough.borough.all();
        Users u = Users.find.where().eq("user_id",Long.parseLong(session("user_id"))).findUnique();
        List<Tools> tool = Tools.find_tools.where().eq("tool_owner", u).findList();
        HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
        HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
        return ok(index.render("", toolCar, boroughs, tools_borough, tools_inCat, displayInventory.render(tool)));
    }


    public Result currentBorrow(){
        List<ToolCategory> toolCar = ToolCategory.find.all();
        List<Borough> boroughs = Borough.borough.all();
        Users u = Users.find.where().eq("user_id",Long.parseLong(session("user_id"))).findUnique();
        List<Tools> tool = Tools.find_tools.where().eq("tool_borrower", u).findList();
        HashMap<Integer, Integer> tools_borough = Application.getToolsinBorough(boroughs);
        HashMap<Long, Integer> tools_inCat = Application.getToolsInCategory(toolCar);
        return ok(index.render("", toolCar, boroughs, tools_borough, tools_inCat, displayCurrentBorrow.render(tool)));
    }

    public Result requested(){
        Users u = Users.find.where().eq("user_id",Long.parseLong(session("user_id"))).findUnique();
        List<Request_Tool> tools = Request_Tool.requested_tool.where().eq("owner",u).findList();
        List<Request_Tool> outGo = Request_Tool.requested_tool.where().eq("user", u).findList();
        HashMap<Integer, Request_Tool> incoming_Requests = incoming(tools);
        HashMap<Integer, Request_Tool> outgoing_Requests = outgoing(outGo);
        return ok(request.render(incoming_Requests, outgoing_Requests));
    }


}
