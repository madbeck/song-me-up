package edu.brown.cs.jmst.music;

import java.util.List;

import edu.brown.cs.jmst.beans.Entity;

public class Artist extends Entity {
  
    public Artist(String uri, List<String> genres, String id, String name, int popularity, String type) {
      this.genres = genres;
      this.id = id;
      this.name = name;
      this.popularity = popularity;
      this.type = type;
      this.uri = uri;
    }
    
    private List<String> genres;
    private String id;
    private String uri;
    private String name;
    private int popularity;
    private String type;
}
