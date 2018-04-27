package edu.brown.cs.jmst.spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.jmst.party.Party;
import edu.brown.cs.jmst.party.PartyException;
import edu.brown.cs.jmst.party.User;
import edu.brown.cs.jmst.songmeup.SmuState;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class JoinHandler implements TemplateViewRoute {

  private SmuState state;

  public JoinHandler(SmuState state) {
    this.state = state;
  }

  @Override
  public ModelAndView handle(Request req, Response res) throws Exception {
    User u = state.getUser(req.session().id());
    if (!u.loggedIn()) {
      res.redirect("/login");
    } else {
      QueryParamsMap qm = req.queryMap();
      String party_id = qm.value("party_id");
      SparkErrorEnum err = null;
      try {
        Party p = state.addPartyPerson(u, party_id);
        Map<String, Object> variables =
            new ImmutableMap.Builder<String, Object>()
                .put("hostname", p.getHostName()).build();
        return new ModelAndView(variables, "songmeup/join/join.ftl");
      } catch (IllegalArgumentException e) {
        err = SparkErrorEnum.INVALID_PARTY_ID;
        // List<BasicNameValuePair> pairs = new ArrayList<>();
        // pairs.add(new BasicNameValuePair("error",
        // SparkErrorEnum.INVALID_PARTY_ID.toString()));
        // res.redirect("/error?" + URLEncodedUtils.format(pairs, "UTF-8"));
      } catch (PartyException pe) {
        err = SparkErrorEnum.ALREADY_IN_PARTY;
        // List<BasicNameValuePair> pairs = new ArrayList<>();
        // pairs.add(new BasicNameValuePair("error",
        // SparkErrorEnum.ALREADY_IN_PARTY.toString()));
        // res.redirect("/error?" + URLEncodedUtils.format(pairs, "UTF-8"));
      }
      if (err != null) {
        List<BasicNameValuePair> pair = new ArrayList<>();
        pair.add(new BasicNameValuePair("error", err.toString()));
        res.redirect("/error?" + URLEncodedUtils.format(pair, "UTF-8"));
      }
    }
    return null;
  }

}
