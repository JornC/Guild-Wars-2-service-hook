package nl.jorncruijsen.guildwars.service.json;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONWvWMap {
  private String type;
  private int[] scores;
  private ArrayList<JSONWvWObjective> objectives;

  public String getType() {
    return type;
  }

  public int[] getScores() {
    return scores;
  }

  public ArrayList<JSONWvWObjective> getObjectives() {
    return objectives;
  }

  @Override
  public String toString() {
    return "JSONWvWMap [type=" + getType() + ", scores=" + Arrays.toString(getScores()) + ", objectives=" + getObjectives() + "]";
  }
}
