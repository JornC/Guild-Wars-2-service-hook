package nl.jorncruijsen.guildwars.service.json;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONWvWMap {
    private String type;
    private int[] scores;
    private ArrayList<JSONWvWObjective> objectives;
    
    @Override
    public String toString() {
      return "JSONWvWMap [type=" + type + ", scores=" + Arrays.toString(scores) + ", objectives=" + objectives + "]";
    }

}
