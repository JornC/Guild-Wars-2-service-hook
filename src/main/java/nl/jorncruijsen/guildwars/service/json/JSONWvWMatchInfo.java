package nl.jorncruijsen.guildwars.service.json;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONWvWMatchInfo {
  private String match_id;
  private int[] scores;
  private ArrayList<JSONWvWMap> maps;

  public int[] getScores() {
    return scores;
  }

  public String getMatch_id() {
    return match_id;
  }

  public ArrayList<JSONWvWMap> getMaps() {
    return maps;
  }

  @Override
  public String toString() {
    return "JSONWvWMatchInfo [match_id=" + match_id + ", scores=" + Arrays.toString(scores) + ", maps=" + getMaps() + "]";
  }
}
