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

  public static void setHandlers(FreeMarkerEngine freeMarker) {
    Spark.webSocket("/songupdates", PartyWebSocket.class);
    Spark.get("/main", new MainPage(), freeMarker);
    Spark.get("/songmeup", new PreMainPage(), freeMarker);

    Spark.get("/logout", new LogoutHandler(), freeMarker);
    Spark.get("/login", new LoginHandler(), freeMarker);
    Spark.get(SpotifyAuthentication.REDIRECT_HANDLE, new CallbackHandler(),
        freeMarker);
    
    // PRE PLAYLIST PAGE
    Spark.get("/form", new PartyFormHandler(), freeMarker); // leads to joe's party form, fill out before creating party
    Spark.get("/host", new HostHandler(), freeMarker); // leads to joe's "you have made a party page" pre-playlist
    
    // PLAYLIST PAGES (2)
    Spark.get("/admin", new AdminPageHandler(), freeMarker); // leads to joe's own playlist page
    Spark.get("/join", new JoinHandler(), freeMarker); // leads to user's playlist page
    
    // GENERATE INFO FOR PLAYLIST PAGE
    Spark.post("/suggestions", new SongSuggestor());
    Spark.post("/refresh", new RefreshToken());
    Spark.post("/playlist", new PlaylistHandler()); // reloads party playlist on page reload

    Spark.get("/player", new PlayerPage(), freeMarker);
    Spark.get("/error", new ErrorHandler(), freeMarker);

    // NOT RELEVANT, will delete later
    Spark.get("/playlists", new MockPlaylist(), freeMarker);    
  }

}
