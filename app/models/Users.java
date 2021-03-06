package models;

/**
 * Created by user on 11/5/2015.
 */

import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.*;
import java.util.*;

@Table(name="users")
@Entity
public class Users extends Model{

    @Id
    public Long user_id;

    @Constraints.Required
    public String first_name;

    @Constraints.Required
    public String last_name;

    @Constraints.Required
    @Column(unique=true)
    public String emailAdrs;

    @Constraints.Required
    public String password;


    @OneToMany
    public List<Comment> comments;

    @OneToMany
    public List<Borrowed> users_borrowed_tools;

    public static Finder<Long,Users> find = new Finder<Long,Users>( Users.class );

    public String getID(){
        return this.user_id.toString();
    }

    public String getName(){
        return first_name+" "+last_name;
    }

    public String getEmail(){return emailAdrs;}

    public boolean authenticate(Users u, String password) {
        if(u != null){
            return BCrypt.checkpw(password, u.password);
        }else{
            return false;
        }
    }

    public static Users createNewUser(String fn, String ln, String username, String password) {
        if(fn.trim().length() == 0 && ln.trim().length() == 0){
            return null;
        }
        if(password == null || username == null || (password.trim().length() < 8 && username.trim().equals(""))) {
            return null;
        }

        // Create a password hash
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Users user = new Users();
        user.first_name = fn;
        user.last_name = ln;
        user.emailAdrs = username;
        user.password = passwordHash;

        return user;
    }

    public static String encrypePassword(String password){
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        return passwordHash;
    }
}
