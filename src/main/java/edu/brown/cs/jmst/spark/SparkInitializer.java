package edu.brown.cs.jmst.spark;

import com.google.gson.Gson;

import edu.brown.cs.jmst.sockets.PartyWebSocket;
import edu.brown.cs.jmst.spotify.SpotifyAuthentication;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Adds all of the various spark handlers.
 *
 * @author Samuel Oliphant
 *
 */
public class SparkInitializer {

  public static final Gson GSON = new Gson();

  public static void setHandlers(FreeMarkerEngine freeMarker, boolean web) {
    String prefix = "/~jmst";
    prefix = "";
    Spark.webSocket("/songupdates", PartyWebSocket.class);
    // if (web) {
    // Spark.webSocket("/~jmst/songupdates", PartyWebSocket.class);
    // Spark.webSocket("/songupdates", PartyWebSocket.class);
    // } else {
    // Spark.webSocket("/songupdates", PartyWebSocket.class);
    // }

    Spark.get(prefix + "/main", new MainPage(), freeMarker);
    Spark.get(prefix + "/songmeup", new PreMainPage(), freeMarker);

    Spark.get(prefix + "/logout", new LogoutHandler(), freeMarker);
    Spark.get(prefix + "/login", new LoginHandler(), freeMarker);
    Spark.get(SpotifyAuthentication.REDIRECT_HANDLE, new CallbackHandler(),
        freeMarker);

    // PRE PLAYLIST PAGE
    // below leads to joe's party form, fill out before creating party
    Spark.get(prefix + "/form", new PartyFormHandler(), freeMarker);
    Spark.post(prefix + "/spotifyPlaylists", new PlaylistSuggestor());
    // below leads to joe's "you have made a party page"
    Spark.get(prefix + "/host", new HostHandler(), freeMarker);

    // PLAYLIST PAGES (2)
    // Spark.get(prefix + "/admin", new AdminPageHandler(), freeMarker);
    // below leads to user's playlist page
    Spark.get(prefix + "/join", new JoinHandler(), freeMarker);

    // GENERATE INFO FOR PLAYLIST PAGE
    Spark.post(prefix + "/suggestions", new SongSuggestor());
    Spark.post(prefix + "/refresh", new RefreshToken());
    // below reloads party playlist on page reload
    Spark.post(prefix + "/playlist", new PlaylistHandler());

    Spark.get(prefix + "/player", new PlayerPage(), freeMarker);
    Spark.get(prefix + "/error", new ErrorHandler(), freeMarker);
    Spark.get(prefix + "/faq", new FAQPage(), freeMarker);

    // NOT RELEVANT, will delete later
    Spark.get(prefix + "/playlists", new MockPlaylist(), freeMarker);
  }

}
