package nl.jorncruijsen.guildwars.service.domain;

public class World {
  public enum TYPE {
    RED, GREEN, BLUE;
  }

  private int id;
  private String name;
  private TYPE type;
  private WvWMap map;
  private WvWMatch match;

  public WvWMatch getMatch() {
    return match;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public TYPE getType() {
    return type;
  }

  public WvWMap getMap() {
    return map;
  }

  @Deprecated
  public void setMap(WvWMap map) {
    this.map = map;
  }

  @Deprecated
  public void setName(String name) {
    this.name = name;
  }

  @Deprecated
  public void setType(TYPE type) {
    this.type = type;
  }

  @Deprecated
  public void setId(int id) {
    this.id = id;
  }

  @Deprecated
  public void setMatch(WvWMatch match) {
    this.match = match;
  }

  @Override
  public String toString() {
    return "World [id=" + id + ", name=" + name + ", type=" + type + "]";
  }
}
