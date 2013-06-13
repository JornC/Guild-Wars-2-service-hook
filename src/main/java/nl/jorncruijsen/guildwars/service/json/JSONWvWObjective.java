package nl.jorncruijsen.guildwars.service.json;

public class JSONWvWObjective {
  int id;
  String owner;
  String owner_guild;

  @Override
  public String toString() {
    return "JSONWvWObjective [id=" + id + ", owner=" + owner + ", owner_guild=" + owner_guild + "]";
  }
}
