package models;

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

@Table(name="passwordresetrequest")
@Entity
public class PasswordResetRequest extends Model{

	@Id
    public Integer pass_id;

    @Constraints.Required
    public String user_email;

    @Constraints.Required
    public DateTime datetime_requested;

    @Constraints.Required
    public Long token;

    public static Finder<Integer,PasswordResetRequest> passresreq = new Finder<Integer,PasswordResetRequest>(PasswordResetRequest.class);


    public static PasswordResetRequest requestSubmitted(String email, Long hashed){
    	PasswordResetRequest prr = new PasswordResetRequest();
    	prr.user_email = email;
    	prr.datetime_requested = new DateTime();
    	prr.token = hashed;
    	return prr;
    }

}