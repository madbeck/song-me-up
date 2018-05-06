package edu.brown.cs.jmst.spark;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.jmst.general.General;
import edu.brown.cs.jmst.party.PartyException;
import edu.brown.cs.jmst.party.User;
import edu.brown.cs.jmst.songmeup.SmuState;
import edu.brown.cs.jmst.spotify.SpotifyAuthentication;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class MainPage implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    SmuState state = SmuState.getInstance();
    String userid = req.session().attribute("user");
    User u = state.getUser(userid);
    if (u == null || !u.loggedIn()) {
      res.redirect(SpotifyAuthentication.getRootUri() + "/songmeup");
      return null;
    }
    QueryParamsMap qm = req.queryMap();
    if (qm.hasKey("leave")) {
      try {
        state.leaveParty(u, u.getCurrentParty());
      } catch (PartyException e) {
        General.printErr("Could not leave party.");
      }
    }
    String name = u.getName() == null ? u.getId() : u.getName();
    Map<String, Object> variables = new ImmutableMap.Builder<String, Object>()
        .put("premium", u.isPremium()).put("name", name).build();
    return new ModelAndView(variables, "songmeup/main_page/index.ftl");
  }

}
