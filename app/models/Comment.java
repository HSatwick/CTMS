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

@Table(name="comments")
@Entity
public class Comment extends Model {

    @Id
    public String comment_id;

    @Constraints.Required
    @ManyToOne
    public Users user;

    @Constraints.Required
    @ManyToOne
    public Tools tool;

    @Constraints.Required
    public String body;

    @Constraints.Required
    public DateTime datetime_posted;

}
