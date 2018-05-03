package edu.brown.cs.jmst.music;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.jmst.beans.EntityBean;

public class TrackBean extends EntityBean implements Track {
  // NOTE: because this extends EntityBean, it already extends Entity. getId()
  // is a default method and does not need to be re-implemented.
  private boolean explicit;
  // private boolean playable;
  private int popularity;
  private int duration_ms;
  private String name;
  private List<String> artistIds;
  private String album_id;
  private String uri;
  private String album_cover;

  public TrackBean(String id, String name, Boolean explicit, int popularity,
      int duration_ms, List<String> artistIds, String album_id, String uri, String album_cover) {
    this.id = id;
    this.uri = uri;
    this.explicit = explicit;
    this.popularity = popularity;
    this.duration_ms = duration_ms;
    this.artistIds = artistIds;
    // this.playable = playable;
    this.album_id = album_id;
    this.name = name;
  }
  

  @Override
  public String getAlbumId() throws Exception {
    return this.album_id;
  }

  @Override
  public List<String> getArtistIds() throws Exception {
    //TODO: why are we returning a new list instead of this.artistIds?
    List<String> artists = new ArrayList<>();
    artists.addAll(this.artistIds);
    return artists;
  }

  @Override
  public boolean isExplicit() throws Exception {
    return this.explicit;
  }

  // @Override
  // public boolean isPlayable() throws Exception {
  // return this.playable;
  // }

  @Override
  public int getPopularity() throws Exception {
    return this.popularity;
  }

  @Override
  public int getDuration_ms() throws Exception {
    return this.duration_ms;
  }

  @Override
  public String getName() throws Exception {
    return this.name;
  }

  @Override
  public String getUri() throws Exception {
    return String.format("https://api.spotify.com/tracks/%s", this.id);
  }

  @Override
  public String toString() {
    return String.format("Name: '%s', Id: '%s', uri: '%s'", this.name, this.id, this.uri);
  }

  @Override
  public String getAlbumArt() {
    return album_cover;
  }

}
