package nl.jorncruijsen.guildwars.service.domain;

import nl.jorncruijsen.guildwars.service.domain.World.TYPE;

public class WvWObjective extends Objective {
  private TYPE owner;

  // TODO Use Guild API
  private String guild;

  public TYPE getOwner() {
    return owner;
  }

  public String getGuild() {
    return guild;
  }

  @Deprecated
  public void setOwner(TYPE owner) {
    this.owner = owner;
  }

  @Deprecated
  public void setGuild(String guild) {
    this.guild = guild;
  }
}
