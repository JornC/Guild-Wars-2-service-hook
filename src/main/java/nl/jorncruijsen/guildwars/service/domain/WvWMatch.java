package nl.jorncruijsen.guildwars.service.domain;

import java.util.HashMap;
import java.util.Map;

import nl.jorncruijsen.guildwars.service.domain.World.TYPE;

public class WvWMatch {
  private String id;
  private final Map<TYPE, World> worlds = new HashMap<>();
  private final Map<TYPE, Integer> scores = new HashMap<>();
  
  public World getWorld(TYPE type) {
    return worlds.get(type);
  }
  
  public void setWorld(TYPE type, World world) {
    worlds.put(type, world);
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void addWorld(World world) {
    setWorld(world.getType(), world);
  }

  public void setScore(TYPE type, int i) {
    scores .put(type, i);
  }

  @Override
  public String toString() {
    return "WvWMatch [id=" + id + ", worlds=" + worlds + "]";
  }
}
