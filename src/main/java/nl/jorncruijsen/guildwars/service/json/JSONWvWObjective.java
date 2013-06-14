package nl.jorncruijsen.guildwars.service.json;

public class JSONWvWObjective {
  private int id;
  private String owner;
  private String owner_guild;

  public int getId() {
    return id;
  }

  public String getOwner() {
    return owner;
  }

  public String getOwnerGuild() {
    return owner_guild;
  }

  @Override
  public String toString() {
    return "JSONWvWObjective [id=" + getId() + ", owner=" + getOwner() + ", owner_guild=" + getOwnerGuild() + "]";
  }
}
