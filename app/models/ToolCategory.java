package models;

/**
 * Created by user on 11/14/2015.
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


@Table(name="categories")
@Entity
public class ToolCategory extends Model{

    @Id
    public String cat_id;

    @Constraints.Required
    public String cat_name;


    public static Finder<String,ToolCategory> find = new Finder<String,ToolCategory>(String.class,ToolCategory.class);

}
