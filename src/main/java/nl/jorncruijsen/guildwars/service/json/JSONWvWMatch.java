package nl.jorncruijsen.guildwars.service.json;

public class JSONWvWMatch {
  private String wvw_match_id;
  private int red_world_id;
  private int blue_world_id;
  private int green_world_id;
  private String start_time;
  private String end_time;

  public String getWvwMatchId() {
    return wvw_match_id;
  }

  public int getRedWorldId() {
    return red_world_id;
  }

  public int getBlueWorldId() {
    return blue_world_id;
  }

  public int getGreenWorldId() {
    return green_world_id;
  }

  public String getStartTime() {
    return start_time;
  }

  public String getEndTime() {
    return end_time;
  }

  @Override
  public String toString() {
    return "WvWMatch [wvw_match_id=" + wvw_match_id + ", red_world_id=" + red_world_id + ", blue_world_id=" + blue_world_id + ", green_world_id=" + green_world_id + ", start_time=" + start_time + ", end_time=" + end_time + "]";
  }

  public boolean containsWorldId(int worldId) {
    return green_world_id == worldId || blue_world_id == worldId || red_world_id == worldId;
  }
}
