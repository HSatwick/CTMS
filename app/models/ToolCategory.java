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
import java.util.*;


@Table(name="categories")
@Entity
public class ToolCategory extends Model{

    @Id
    public Long cat_id;

    @Constraints.Required
    public String cat_name;


    @OneToMany
    public List<Tools> consistsOf;


    public static Finder<Long,ToolCategory> find = new Finder<Long,ToolCategory>(ToolCategory.class);



}
