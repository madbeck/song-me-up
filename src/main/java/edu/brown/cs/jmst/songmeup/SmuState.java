package edu.brown.cs.jmst.songmeup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.jmst.party.Party;
import edu.brown.cs.jmst.party.User;
import edu.brown.cs.jmst.spotify.SpotifyAuthentication;

/**
 * Storage class for references to all active parties, party hosts, etc.
 *
 * @author Samuel Oliphant
 *
 */
public class SmuState {

  private List<String> listMessage;
  private String message;
  private Map<String, Party> parties =
      Collections.synchronizedMap(new HashMap<>());
  private Map<String, User> users =
      Collections.synchronizedMap(new HashMap<>());

  /**
   * Add a party to the set.
   *
   * @param party
   *          party to add
   */
  public String addParty(Party party) {
    String key = SpotifyAuthentication.randomString(6);
    while (parties.containsKey(key)) {
      key = SpotifyAuthentication.randomString(6);
    }
    parties.put(key, party);
    return key;
  }

  /**
   * Get party with given id.
   *
   * @param id
   *          id to look for
   * @return party with that id
   */
  public Party getParty(String id) {
    return parties.get(id);
  }

  public User getUser(String id) {
    // General.printVal("Users", Integer.toString(users.size()));
    if (users.containsKey(id)) {
      return users.get(id);
    } else {
      User user = new User();
      users.put(id, user);
      return user;
    }
  }

  /**
   * @return the listMessage
   */
  public List<String> getListMessage() {
    return listMessage;
  }

  /**
   * @param listMessage
   *          the listMessage to set
   */
  public void setListMessage(List<String> listMessage) {
    this.listMessage = listMessage;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  public String getAuth() {
    if (users.size() == 0) {
      throw new IllegalStateException("No users logged in.");
    } else {
      Set<String> ids = users.keySet();
      String id = ids.iterator().next();
      User u = users.get(id);
      if (u.loggedIn()) {
        return u.getAuth();
      } else {
        throw new IllegalStateException("User not logged in.");
      }

    }
  }

}
