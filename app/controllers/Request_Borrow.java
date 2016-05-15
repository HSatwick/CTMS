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

public class Request_Borrow extends Controller{

	public Result tool_requested(Long id){
		Tools tools = Tools.find_tools.where().eq("tool_id",id).findUnique();
		Users user = Users.find.where().eq("user_id", Long.parseLong(session("user_id"))).findUnique();
		Request_Tool rb = new Request_Tool();
		rb.tools = tools;
		rb.user = user;
		rb.owner = tools.tool_owner;
		rb.status = 0;
		rb.save();
		return redirect(routes.SeeMore.displayMore(id));
	}

	public Result request_withdraw(Long id){
		Request_Tool rt = Request_Tool.requested_tool.where().eq("request_tool_id",id).findUnique();
		rt.status = 3;
		rt.delete();
		return redirect(routes.SeeMore.displayMore(rt.tools.tool_id));
	}

	public Result request_cancel(Long id){
		Request_Tool rt = Request_Tool.updates(id, 2);
		rt.delete();
		return redirect(routes.Search.requested());
	}
}