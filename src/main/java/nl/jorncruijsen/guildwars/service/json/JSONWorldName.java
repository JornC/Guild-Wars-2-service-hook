package nl.jorncruijsen.guildwars.service.json;

public class JSONWorldName {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "WorldName [id=" + id + ", name=" + name + "]";
  }
}
