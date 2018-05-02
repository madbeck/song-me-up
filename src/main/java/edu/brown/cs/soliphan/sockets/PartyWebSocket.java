package edu.brown.cs.soliphan.sockets;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.brown.cs.jmst.party.Party;
import edu.brown.cs.jmst.party.PartyException;
import edu.brown.cs.jmst.party.User;
import edu.brown.cs.jmst.songmeup.SmuState;

@WebSocket
public class PartyWebSocket {
  private static final Gson GSON = new Gson();
  private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

  private static enum MESSAGE_TYPE {
    VOTESONG, ADDSONG, REMOVESONG, PLAYLIST
  }

  @OnWebSocketConnect
  public void connected(Session session) throws IOException {
    // Add the session to the queue
    sessions.add(session);
    // // Build the CONNECT message
    // JsonObject jpayload = new JsonObject();
    // jpayload.addProperty("id", nextId);
    // JsonObject jo = new JsonObject();
    // jo.addProperty("type", MESSAGE_TYPE.CONNECT.ordinal());
    // jo.add("payload", jpayload);
    // nextId++;
    // // Send the CONNECT message
    // session.getRemote().sendString(GSON.toJson(jo));
  }

  @OnWebSocketClose
  public void closed(Session session, int statusCode, String reason) {
    // Remove the session from the queue
    sessions.remove(session);
  }

  @OnWebSocketMessage
  public void message(Session session, String message) throws IOException {
    JsonParser parser = new JsonParser();
    JsonObject received = parser.parse(message).getAsJsonObject();
    assert received.get("type").getAsInt() < 4
        && received.get("type").getAsInt() >= 0;
    SmuState state = SmuState.getInstance();
    MESSAGE_TYPE type = MESSAGE_TYPE.values()[received.get("type").getAsInt()];
    JsonObject inputPayload = received.get("payload").getAsJsonObject();
    String user_id = inputPayload.get("id").getAsString();

    User u = state.getUser(user_id);
    String partyId = u.getCurrentParty();
    if (partyId != null) {
      Party p = state.getParty(partyId);
      switch (type) {
        case VOTESONG:
          String song_id = inputPayload.get("song_id").getAsString();
          boolean vote = inputPayload.get("vote").getAsBoolean();
          try {
            JsonObject jpayload = new JsonObject();
            jpayload.addProperty("song_id", song_id);
            jpayload.addProperty("votes", p.voteOnSong(user_id, song_id, vote));
            JsonObject jo = new JsonObject();
            jo.addProperty("type", MESSAGE_TYPE.VOTESONG.ordinal());
            jo.add("payload", jpayload);
            for (Session s : sessions) {
              s.getRemote().sendString(GSON.toJson(jo));
            }
          } catch (PartyException e) {
            return;
          }
          break;
        case ADDSONG:
          // String song_id = inputPayload.get("song_id").getAsString();
          // boolean vote = inputPayload.get("vote").getAsBoolean();
          // try {
          // JsonObject jpayload = new JsonObject();
          // jpayload.addProperty("song_id", song_id);
          // jpayload.addProperty("votes", p.voteOnSong(user_id, song_id,
          // vote));
          // JsonObject jo = new JsonObject();
          // jo.addProperty("type", MESSAGE_TYPE.VOTESONG.ordinal());
          // jo.add("payload", jpayload);
          // for (Session s : sessions) {
          // s.getRemote().sendString(GSON.toJson(jo));
          // }
          // } catch (PartyException e) {
          // return;
          // }
          break;
        case REMOVESONG:
          break;
        case PLAYLIST:
          break;
      }
    }
  }
}
