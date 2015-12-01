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

import views.html.*;


import static play.data.Form.form;

import static play.libs.Json.toJson;


public class Comments extends Controller{

    public Result getCommentUI(Long id){
        return ok(comment.render(id));
    }

    public Result addComment(Long id){
        System.out.println(id);
        return redirect(routes.Application.index());
    }

}
