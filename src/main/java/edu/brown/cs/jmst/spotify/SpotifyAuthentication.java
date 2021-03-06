package edu.brown.cs.jmst.spotify;

import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpotifyAuthentication {
  public static final String CLIENT_ID = "4dbc736d594345a99c6e00bf776f5464";
  public static final String CLIENT_SECRET = "3a287186a58c4276804beb25a97f12de";
  public static final String REDIRECT_HANDLE = "/callback";
  private static String ROOT_URI;
  private static String REDIRECT_URI;
  public static final String STATE_KEY = "spotify_auth_state";
  public static final String ENCODED_CLIENT_KEY = Base64.getEncoder()
      .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

  private static final String VALID =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String READABLE = "ABCDEFGHJKLMNPQRSTUWXYZ";

  public static String randomString(int length) {
    if (length < 0) {
      throw new IllegalArgumentException();
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(VALID.charAt((int) Math.floor(Math.random() * VALID.length())));
    }
    return sb.toString();
  }

  public static String randomReadableString(int length) {
    if (length < 0) {
      throw new IllegalArgumentException();
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(
          READABLE.charAt((int) Math.floor(Math.random() * READABLE.length())));
    }
    return sb.toString();
  }

  public static void setRootUri(String uri) throws Exception {
    if (ROOT_URI == null) {
      ROOT_URI = uri;
      REDIRECT_URI = ROOT_URI + REDIRECT_HANDLE;
    } else {
      throw new Exception("Cannot set root uri twice.");
    }
  }

  public static String getRootUri() {
    return ROOT_URI;
  }

  public static String getRedirectUri() {
    return REDIRECT_URI;
  }

  private static Set<String> statekeys =
      Collections.synchronizedSet(new HashSet<>());

  public static synchronized String getNewState() {
    String key = SpotifyAuthentication.randomString(16);
    while (statekeys.contains(key)) {
      key = SpotifyAuthentication.randomString(16);
    }
    statekeys.add(key);
    return key;
  }

  public static synchronized boolean hasState(String testString) {
    return statekeys.remove(testString);
  }

}
