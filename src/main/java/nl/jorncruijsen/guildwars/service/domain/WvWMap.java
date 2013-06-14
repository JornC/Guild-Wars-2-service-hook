package nl.jorncruijsen.guildwars.service.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.jorncruijsen.guildwars.service.domain.World.TYPE;

public class WvWMap {
  private TYPE type;
  private ArrayList<WvWObjective> objectives;
  private final Map<TYPE, Integer> scores = new HashMap<>();

  public Map<TYPE, Integer> getScore() {
    return scores;
  }

  public Integer getScore(TYPE type) {
    return scores.get(type);
  }

  public ArrayList<WvWObjective> getObjectives() {
    return objectives;
  }

  public TYPE getType() {
    return type;
  }

  @Deprecated
  public void putScore(TYPE red, int score) {
    scores.put(type, score);
  }

  @Deprecated
  public void setObjectives(ArrayList<WvWObjective> objectives) {
    this.objectives = objectives;
  }

  @Deprecated
  public void setType(TYPE type) {
    this.type = type;
  }
}
