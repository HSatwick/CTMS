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

@Table(name="boroughs")
@Entity
public class Borough extends Model{

    @Id
    public Integer bor_id;

    @Constraints.Required
    public String bor_name;

    public static Finder<Integer,Borough> borough = new Finder<Integer,Borough>(Borough.class);

}
