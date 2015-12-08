package models;

/**
 * Created by user on 11/17/2015.
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
import org.joda.time.*;
import java.util.*;

@Table(name="borrowed")
@Entity
public class Borrowed extends Model {

    @Id
    public int id;

    @Constraints.Required
    @ManyToOne
    public Users users;

    @Constraints.Required
    @ManyToOne
    public Tools tools;

    public static Finder<Integer,Borrowed> borrowed = new Finder<Integer,Borrowed>(Borrowed.class);


    public static Borrowed borrowedTool(Users user, Tools tools){
        Borrowed borrow = new Borrowed();

        borrow.users = user;
        borrow.tools = tools;

        return borrow;
    }
}
