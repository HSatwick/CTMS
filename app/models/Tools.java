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
    public Long tool_id;

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
    public int available; //1 - not borrowed, 0 - borrowed

    @ManyToOne
    public Users tool_borrower;

    public static Finder<Long,Tools> find_tools = new Finder<Long,Tools>(Tools.class);

    public static Tools uploadTool(String name, String desc, ToolCategory cate, Users user, Borough borough, int ava){
        Tools tool = new Tools();
        tool.tool_name = name;
        tool.tool_desc = desc;
        tool.tool_type = cate;
        tool.tool_owner = user;
        tool.borough = borough;
        tool.available = ava;

        return tool;

    }



}