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

@Table(name="request_tool")
@Entity
public class Request_Tool extends Model{

	@Id
	public long request_tool_id;

	@Constraints.Required
	@ManyToOne
	public Users user; //user info who requested the tool

	@ManyToOne
	public Users owner; //owner of the tool to whom the request was made

	@Constraints.Required
	@ManyToOne
	public Tools tools; //tool that is requested

	@Constraints.Required
	public int status; // 0 - requested, 1 - accepted, 2 - cancelled, 3 - withdrawn

	public static Finder<Long,Request_Tool> requested_tool = new Finder<Long,Request_Tool>(Request_Tool.class);

	public static Request_Tool updates(long t, int state){
		Request_Tool rt = Request_Tool.requested_tool.where().eq("request_tool_id",t).findUnique();
		rt.status = state;
		return rt;
	}
}