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

public class Application extends Controller {

    public static String user = "";
    public static String status = "Sign In", newStatus = "Logout";


    //support functions**************************************************************************
    public boolean validateEmail(String username){
        String split_username[] = username.split("@");
        if(split_username.length == 2){
            String leftSide[] = split_username[0].split("\\.");
            String rightSide[] = split_username[1].split("\\.");
            if(leftSide.length >= 1 && leftSide.length <= 2 && rightSide.length == 2){
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    public static boolean isActiveAndinTimeRange(PasswordResetRequest p){
        if(p == null){
            return false;
        }else{
            DateTime current = new DateTime();
            Period per = new Period(p.datetime_requested, current);
            int hours = per.getHours();
            System.out.println(hours);
            if(hours >=24){
                return false;
            }else{
                return true;
            }
        }
    }

    public static void connect_save_send(String receiver, String body, String title){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); 
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("communitytoolssharing@gmail.com", "ctms2016");
            }
        });
        try{
            String from = "communitytoolssharing@gmail.com";
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(title);
            message.setContent(body, "text/html");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }   
    }

    public static HashMap<Integer, Integer> getToolsinBorough(List<Borough> b){
        HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
        for(int i=0; i<b.size(); i++){
            List<Tools> total_elements = Tools.find_tools.where().eq("borough",b.get(i)).eq("available",1).findList();
            if(total_elements != null){
                list.put(b.get(i).bor_id,total_elements.size());
            }else{
                list.put(b.get(i).bor_id,0);
            }
        }
        return list;
    }

    public static HashMap<Long, Integer> getToolsInCategory(List<ToolCategory> tc){
        HashMap<Long,Integer> list = new HashMap<Long,Integer>();
        for(int i=0; i<tc.size(); i++){
            List<Tools> tools_in_cat = Tools.find_tools.where().eq("tool_type",tc.get(i)).eq("available",1).findList();
            if(tools_in_cat != null){
                list.put(tc.get(i).cat_id, tools_in_cat.size());
            }else{
                list.put(tc.get(i).cat_id, 0);
            }
        }
        return list;
    }

    public static boolean uniqueToken(Long tokens){
        PasswordResetRequest req = PasswordResetRequest.passresreq.where().eq("token",tokens).findUnique();
        if(req == null){
            return true;
        }
        return false;
    }
    //*************************************************************************************

    //controller functions
    public Result index() {
        List<ToolCategory> toolCar = ToolCategory.find.all();
        List<Borough> boroughs = Borough.borough.all();
        HashMap<Integer, Integer> tools_borough = getToolsinBorough(boroughs);
        HashMap<Long, Integer> tools_inCat = getToolsInCategory(toolCar);
        return ok(index.render("",toolCar, boroughs, tools_borough, tools_inCat, search.render()));
    }

    public Result showUserForm() {
        return ok(user_login.render("Community Tool Management System"));
    }

    public Result signUp(){
        DynamicForm userForm = form().bindFromRequest();
        String firstName = userForm.data().get("first_name");
        String lastName = userForm.data().get("last_name");
        String username = userForm.data().get("emailAdrs");
        String password = userForm.data().get("password");


        Users user = Users.createNewUser(firstName, lastName, username, password);

        if(user == null) {
            flash("error", "Invalid user");
            return redirect(routes.Application.showUserForm());
        }else if(user != null){
            if(validateEmail(username)){
                List<Users> u = Users.find.where().eq("emailAdrs", username).findList();
                if(u.size() != 0){
                    flash("error", "email already exists");
                    return redirect(routes.Application.showUserForm());
                }
            }else{
                flash("error", "Please enter a valid email address");
                return redirect(routes.Application.showUserForm());
            }
        }

        user.save();
        String title = "Welcome to Community Tool Management Systems";
        String content = "Dear "+firstName+" "+lastName+",<br><br>";
        content += "CTMS happily welcomes you as a member. We hope that you find the right deals/traders ";
        content += "in the market through CTMS. For any other information or personal assistance please contact ";
        content += "the following email address communitytoolssharing@gmail.com and we'll get back to you as soon as ";
        content += "we can. <br>";
        content += "Thank You for choosing CTMS. <br><br>";
        content += "Sincerely,<br><br>CTMS - Community Tool Management Systems";
        connect_save_send(user.emailAdrs,content, title);

        flash("success", user.getName());
        session("user_id", user.getID());
        session(user.getID(),user.getName());

        return redirect(routes.Application.index());
    }

    public Result logIn(){
        DynamicForm userForm = form().bindFromRequest();
        String username = userForm.data().get("emailAdrs");
        String password = userForm.data().get("password");

        Users user = Users.find.where().eq("emailAdrs", username).findUnique();

        if(user != null) {
            if(user.authenticate(user, password)) {
                session("user_id", user.getID());
                session(user.getID(),user.getName());

                flash("success", "back " + user.getName());
            }else{
                flash("error", "Invalid password.");
                return redirect(routes.Application.showUserForm());
            }
        } else {
            flash("error", "Invalid username.");
            return redirect(routes.Application.showUserForm());
        }
        return redirect(routes.Application.index());
    }

    public Result logout() {
        user = session("user_id");
        session().remove(user);
        user = "";
        session().remove("user_id");
        return redirect(routes.Application.index());
    }

    public Result forgotPassword(){
        return ok(forgotpassword.render());
    }

    public Result resetPasswordRequest(){
        DynamicForm userForm = form().bindFromRequest();
        String emailAddress = userForm.data().get("enteremail");
        if(emailAddress.trim().length() > 0){
            Users user = Users.find.where().eq("emailAdrs", emailAddress).findUnique();
            if(user != null){
                Random r = new Random();
                long newToken = r.nextLong();
                while(!uniqueToken(newToken)){
                    newToken = r.nextLong();
                }
                if(newToken < 0){
                    newToken *= -1;
                }
                String title = "Link to Renew your password for Community Tool Management Systems";
                String content = "Dear "+user.getName()+",<br><br>";
                content += "Click on the following link to reset your password.<br>";
                content += "<a href='https://mysterious-atoll-4309.herokuapp.com/newpass/"+newToken+"'>Change Password</a><br>";
                content += "<br><br>This link is only valid for 24 hours.<br><br>";
                content += "Sincerely,<br><br>CTMS - Community Tool Management Systems";
                connect_save_send(emailAddress,content,title);
                PasswordResetRequest preq = PasswordResetRequest.passresreq.where().eq("user_email",emailAddress).findUnique();
                if(preq != null){
                    preq.delete();
                }
                PasswordResetRequest prr = PasswordResetRequest.requestSubmitted(emailAddress, newToken);
                prr.save();
            }else{
                flash("error", "Couldn't find an account with associated email");
                return redirect(routes.Application.forgotPassword());
            }
        }else{
            flash("error", "The field is required");
            return redirect(routes.Application.forgotPassword());
        }
        return redirect(routes.Application.showUserForm());
    }

    public Result obtainUrl(Long code){
        PasswordResetRequest pr = PasswordResetRequest.passresreq.where().eq("token",code).findUnique();
        if(pr == null){
            return redirect(routes.Application.forgotPassword());
        }
        Users name = Users.find.where().eq("emailAdrs",pr.user_email).findUnique();
        return ok(resetPassword.render(code, name.getName()));
    }

    public Result submitPassword(Long token){
        DynamicForm userForm = form().bindFromRequest();
        String passwordOne = userForm.data().get("password1");
        String passwordTwo = userForm.data().get("password2");
        if(passwordOne == null || passwordOne.trim().length() < 8){
            flash("warning","The password needs to be atleast 8 characters long");
            return redirect(routes.Application.obtainUrl(token));
        }
        if(!passwordOne.equals(passwordTwo)){
            flash("warning","The two password fields don't match up.");
            return redirect(routes.Application.obtainUrl(token));
        }
        //update the user password
        PasswordResetRequest prreq = PasswordResetRequest.passresreq.where().eq("token",token).findUnique();
        Users user = Users.find.where().eq("emailAdrs",prreq.user_email).findUnique();
        if(isActiveAndinTimeRange(prreq)){
            String passwordHash = Users.encrypePassword(passwordOne);
            user.password = passwordHash;
            user.update();
            prreq.delete();
            String title = "Your password at Community Tool Management Systems has been reset";
            String content = "Dear "+user.getName()+",<br><br>";
            content += "Your password has been reset.<br>";
            content += "Thank You for using our website. <br><br>";
            content += "Sincerely,<br><br>CTMS - Community Tool Management Systems";
            connect_save_send(user.emailAdrs,content, title);
        }else{
            if(prreq != null){
                prreq.delete();
                flash("warning","The link has expired. Please re-apply");
                return redirect(routes.Application.obtainUrl(token));
            }
        }
        return redirect(routes.Application.showUserForm());
    }
    //**************************************************************************************************

}
