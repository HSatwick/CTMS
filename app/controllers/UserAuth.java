package controllers;

import models.Users;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by medgardo on 11/10/15.
 */
public class UserAuth extends Security.Authenticator {

    // When return is null, Authentication failed
    @Override
    public String getUsername(final Http.Context ctx) {
        String userIdStr = ctx.session().get("user_id");
        if(userIdStr == null) return null;

        Users user = Users.find.byId(userIdStr);
        return (user != null ? user.getID() : null);
    }

    @Override
    public Result onUnauthorized(final Http.Context ctx) {
        ctx.flash().put("error",
                "Nice try, but you need to log in first!");
        return redirect(routes.Application.index());
    }
}