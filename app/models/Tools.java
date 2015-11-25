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
import java.util.*;

@Table(name="tools")
@Entity
public class Tools extends Model{

    @Id
    public String tool_id;

    @Constraints.Required
    public String tool_name;

    @Constraints.Required
    public String tool_desc;

    @Constraints.Required
    @ManyToOne
    public ToolCategory tool_type;

    @Constraints.Required
    @ManyToOne
    public Users tool_owner;

    @Constraints.Required
    @ManyToOne
    public Borough borough;

    @OneToMany
    public List<Comment> toolComments;

    @OneToMany
    public List<Borrowed> tools_borrowedBy_users;

    @Constraints.Required
    public int available; //0 - not borrowed, 1 - borrowed


    public static Finder<String,Tools> find_tools = new Finder<String,Tools>(String.class,Tools.class);

}