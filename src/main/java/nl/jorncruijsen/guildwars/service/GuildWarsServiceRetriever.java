package nl.jorncruijsen.guildwars.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.jorncruijsen.guildwars.service.domain.World;
import nl.jorncruijsen.guildwars.service.domain.World.TYPE;
import nl.jorncruijsen.guildwars.service.domain.WvWMap;
import nl.jorncruijsen.guildwars.service.domain.WvWMatch;
import nl.jorncruijsen.guildwars.service.domain.WvWObjective;
import nl.jorncruijsen.guildwars.service.json.JSONWorldName;
import nl.jorncruijsen.guildwars.service.json.JSONWvWMap;
import nl.jorncruijsen.guildwars.service.json.JSONWvWMatch;
import nl.jorncruijsen.guildwars.service.json.JSONWvWMatchInfo;
import nl.jorncruijsen.guildwars.service.json.JSONWvWObjective;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("deprecation")
public class GuildWarsServiceRetriever {
  private static final String WORLD_NAMES = "https://api.guildwars2.com/v1/world_names.json";
  private static final String MATCH_OVERVIEW_URL = "https://api.guildwars2.com/v1/wvw/matches.json";
  private static final String MATCH_DETAILS_URL = "https://api.guildwars2.com/v1/wvw/match_details.json?match_id=";

  public static GuildWarsServiceRetriever I = new GuildWarsServiceRetriever();

  private final Map<String, WvWMatch> matches = new HashMap<>();
  private final Map<Integer, World> worlds = new HashMap<>();

  private final JsonParser parser = new JsonParser();
  private final Gson gson = new Gson();

  private GuildWarsServiceRetriever() {
  }

  /**
   * Retrieve match and world info.
   */
  public void init() {
    System.out.println("Retrieving world names...");
    Map<Integer, JSONWorldName> worldNames = retrieveWorldNames();
    System.out.println("Done.");

    System.out.println("Retrieving match overview...");
    Map<String, JSONWvWMatch> matchOverview = retrieveMatchOverview();
    System.out.println("Done.");

    // Stitch world info to match info
    stitch(worldNames, matchOverview);

    System.out.println("Retrieving detailed match info...");
    for (WvWMatch m : matches.values()) {
      retrieveMatchInfo(m);
    }
    System.out.println("Done.");
  }

  private Map<Integer, JSONWorldName> retrieveWorldNames() {
    InputStream is = URLRetriever.getContent(WORLD_NAMES);

    InputStreamReader content = new InputStreamReader(is);
    JsonArray arr = parser.parse(content).getAsJsonArray();

    HashMap<Integer, JSONWorldName> worldNames = new HashMap<>();

    for (int i = 0; i < arr.size(); i++) {
      JSONWorldName entry = gson.fromJson(arr.get(i), JSONWorldName.class);
      worldNames.put(entry.getId(), entry);
    }

    return worldNames;
  }

  private Map<String, JSONWvWMatch> retrieveMatchOverview() {
    InputStream is = URLRetriever.getContent(MATCH_OVERVIEW_URL);
    InputStreamReader content = new InputStreamReader(is);
    JsonArray arr = parser.parse(content).getAsJsonObject().getAsJsonArray("wvw_matches");

    HashMap<String, JSONWvWMatch> matches = new HashMap<>();
    for (int i = 0; i < arr.size(); i++) {
      JSONWvWMatch entry = gson.fromJson(arr.get(i), JSONWvWMatch.class);
      matches.put(entry.getWvwMatchId(), entry);
    }

    return matches;
  }

  public void retrieveMatchInfo(WvWMatch match) {
    InputStream is = URLRetriever.getContent(MATCH_DETAILS_URL + match.getId());
    InputStreamReader content = new InputStreamReader(is);
    JsonObject parsed = parser.parse(content).getAsJsonObject();

    JSONWvWMatchInfo obj = gson.fromJson(parsed, JSONWvWMatchInfo.class);
    int[] scores = obj.getScores();

    // Set world scores
    match.setScore(TYPE.RED, scores[0]);
    match.setScore(TYPE.GREEN, scores[1]);
    match.setScore(TYPE.BLUE, scores[2]);

    for (JSONWvWMap map : obj.getMaps()) {
      match.addMap(createMap(map));
    }
  }

  private WvWMap createMap(JSONWvWMap jsonMap) {
    int[] scores = jsonMap.getScores();
    ArrayList<WvWObjective> objectives = new ArrayList<>();
    for (JSONWvWObjective objective : jsonMap.getObjectives()) {
      objectives.add(createWvWObjective(objective));
    }

    WvWMap map = new WvWMap();
    map.putScore(TYPE.RED, scores[0]);
    map.putScore(TYPE.GREEN, scores[1]);
    map.putScore(TYPE.BLUE, scores[2]);
    map.setObjectives(objectives);
    map.setType(parseHomeType(jsonMap.getType()));

    return map;
  }

  private WvWObjective createWvWObjective(JSONWvWObjective jsonObjective) {
    WvWObjective objective = new WvWObjective();

    objective.setId(jsonObjective.getId());
    objective.setOwner(parseType(jsonObjective.getOwner()));
    objective.setGuild(jsonObjective.getOwnerGuild());

    return objective;
  }

  private TYPE parseHomeType(String str) {
    switch (str) {
    case "redHome":
      return TYPE.RED;
    case "greenHome":
      return TYPE.GREEN;
    case "blueHome":
      return TYPE.BLUE;
    default:
      return null;
    }
  }

  private TYPE parseType(String str) {
    switch (str) {
    case "Red":
      return TYPE.RED;
    case "Green":
      return TYPE.GREEN;
    case "Blue":
      return TYPE.BLUE;
    default:
      return null;
    }
  }

  private void stitch(Map<Integer, JSONWorldName> worldNames, Map<String, JSONWvWMatch> matchOverview) {
    for (JSONWvWMatch jsonMatch : matchOverview.values()) {
      WvWMatch match = new WvWMatch();
      match.setId(jsonMatch.getWvwMatchId());
      match.addWorld(createWorld(TYPE.GREEN, jsonMatch.getGreenWorldId(), match, worldNames));
      match.addWorld(createWorld(TYPE.BLUE, jsonMatch.getBlueWorldId(), match, worldNames));
      match.addWorld(createWorld(TYPE.RED, jsonMatch.getRedWorldId(), match, worldNames));

      matches.put(match.getId(), match);
    }
  }

  private World createWorld(TYPE type, int worldId, WvWMatch match, Map<Integer, JSONWorldName> worldNames) {
    World world = new World();
    world.setId(worldId);
    world.setType(type);
    world.setName(worldNames.get(worldId).getName());
    world.setMatch(match);

    worlds.put(worldId, world);

    return world;
  }

  public Map<String, WvWMatch> getMatches() {
    return matches;
  }

  public Map<Integer, World> getWorlds() {
    return worlds;
  }
}
