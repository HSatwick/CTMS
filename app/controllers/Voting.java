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

public class Voting extends Controller{

	//param: tool id
	public Result updateVote(Long id, Long voting){
		Tools tool = Tools.find_tools.where().eq("tool_id",id).findUnique();
		Users user = Users.find.where().eq("user_id",Long.parseLong(session("user_id"))).findUnique();
		Vote_Tools existant = Vote_Tools.voteFinder.where().eq("tools",tool).eq("user",user).findUnique();
		if(existant == null){
			Vote_Tools vote = Vote_Tools.giveVote(user,tool,voting);
			vote.save();
		}else{
			Vote_Tools updateVote = Vote_Tools.updateVote(existant, voting);
			updateVote.update();
		}
		return redirect(routes.SeeMore.displayMore(id));
	}

}