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

@Table(name="votesTools")
@Entity
public class Vote_Tools extends Model{

	@Id
	public Long vote_id;

	@Constraints.Required
	@ManyToOne
	public Users user;

	@Constraints.Required
	@ManyToOne
	public Tools tools;

	@Constraints.Required
	public long vote;

	public static Finder<Long,Vote_Tools> voteFinder = new Finder<Long, Vote_Tools>(Vote_Tools.class);

	public static Vote_Tools giveVote(Users users, Tools tools, long vote){
		Vote_Tools voteTool = new Vote_Tools();
		voteTool.user = users;
		voteTool.tools = tools;
		voteTool.vote = vote;
		return voteTool;
	}

	public static Vote_Tools updateVote(Vote_Tools tools, long vote){
		tools.vote = vote;
		return tools;
	}
}