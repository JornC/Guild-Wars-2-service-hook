package nl.jorncruijsen.guildwars.service.domain;

import java.util.ArrayList;
import java.util.Map;

import nl.jorncruijsen.guildwars.service.domain.World.TYPE;

public class WvWMap {
  private TYPE type;
  private ArrayList<Objective> objectives;
  private Map<TYPE, Integer> scores;
}
