package nl.jorncruijsen.guildwars.service.domain;

public class World {
  public enum TYPE {
    RED, GREEN, BLUE;
  }

  private int id;
  private String name;
  private TYPE type;
  private WvWMatch match;
  private int score;

  public WvWMatch getMatch() {
    return match;
  }

  public void setMatch(WvWMatch match) {
    this.match = match;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(TYPE type) {
    this.type = type;
  }

  public TYPE getType() {
    return type;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  @Override
  public String toString() {
    return "World [id=" + id + ", name=" + name + ", type=" + type + ", score=" + score + "]";
  }
}
