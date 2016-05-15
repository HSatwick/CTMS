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

public class SeeMore extends Controller{

	public static int checkifBorrowed(Tools tool){
		String user_id = session("user_id");
		if(user_id == null){
			return 0;
		}
		Users user = Users.find.where().eq("user_id",Long.parseLong(user_id)).findUnique();
		List<Borrowed> borrows = Borrowed.borrowed.where().eq("users",user).eq("tools",tool).findList();
		if(borrows.size() == 0){
			return 0;
		}else{
			return 1;
		}
	}

	public static int getPlusVotes(List<Vote_Tools> p, List<Vote_Tools> n){
		if(p == null && n == null){
			return 0;
		}else if(p == null && n != null){
			return -n.size();
		}else if(p != null && n == null){
			return p.size();
		}else{
			return (p.size()-n.size());
		}
	}

	public static int getCodeForReq(Request_Tool r){
		if(r == null){
			return -1;
		}else{
			return 0;
		}
	}

	public Result displayMore(Long id){
		Tools tools = Tools.find_tools.where().eq("tool_id",id).findUnique();
		List<Comment> comments = Comment.find_Comments.where().eq("tool",tools).findList();
		int borrowedByUser = checkifBorrowed(tools);
		List<Vote_Tools> votesPlus = Vote_Tools.voteFinder.where().eq("tools",tools).eq("vote",2).findList();
		List<Vote_Tools> votesMinus = Vote_Tools.voteFinder.where().eq("tools",tools).eq("vote",1).findList();
		int toolvote = getPlusVotes(votesPlus, votesMinus);
		Users u;
		Request_Tool rt = null;
		if(session("user_id") != null){
			u = Users.find.where().eq("user_id",Long.parseLong(session("user_id"))).findUnique();
			rt = Request_Tool.requested_tool.where().eq("tools",tools).eq("user",u).eq("status",0).findUnique();
		}
		int req_code = getCodeForReq(rt);
		return ok(displayContent.render(tools, comments, borrowedByUser, toolvote, req_code, rt));
	}

}