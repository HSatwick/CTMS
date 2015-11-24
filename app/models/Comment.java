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

@Table(name="comments")
@Entity
public class Comment extends Model {

    @Id
    public String comment_id;

    @Constraints.Required
    public Users user;

    @Constraints.Required
    public Tools tool;

}
