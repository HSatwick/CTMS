package controllers;

import models.*;
import play.*;
import play.mvc.*; 
import play.data.Form;
import play.data.DynamicForm;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.joda.time.*;

import views.html.*;


import static play.data.Form.form;

import static play.libs.Json.toJson;

public class Personal extends Controller {

	public Result showPersonalInfo(String id){
		/*
		The personal page consist of the following fields.
		1. Number of tools s/he own.
		2. Number of tools s/he borrowed till date.
		3. Number of tools s/he returned till date.
		*/
		Users user = Users.find.where().eq("user_id",Long.parseLong(id)).findUnique();
		List<Borrowed> borrowed = Borrowed.borrowed.where().eq("users",user).findList();
		List<Tools> tools_owned = Tools.find_tools.where().eq("tool_owner",user).findList();
		List<Borrowed> tools_returned = Borrowed.borrowed.where().eq("users",user).eq("active",0).findList();
		int borrow = 0;
		int numOfToolsOwn = 0;
		int toolsReturned = 0;
		if(borrowed != null){
			borrow = borrowed.size();
		}
		if(tools_owned != null){
			numOfToolsOwn = tools_owned.size();
		}
		if(tools_returned != null){
			toolsReturned = tools_returned.size();
		}
		return ok(personalPage.render(user.getName(),user.getEmail(),borrow, numOfToolsOwn, toolsReturned));
	}

}