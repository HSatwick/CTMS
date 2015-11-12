package models;

/**
 * Created by user on 11/5/2015.
 */

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users extends Model{

    @Id
    public String id;

    public String first_name;

    public String last_name;

    public String emailAdrs;

    public String password;

    public static Finder<String,Users> find = new Finder<String,Users>(
            String.class, Users.class
    );

    public String getName(){
        return first_name+" "+last_name;
    }
}
