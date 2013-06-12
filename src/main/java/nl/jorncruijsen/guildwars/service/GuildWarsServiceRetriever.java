package nl.jorncruijsen.guildwars.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import nl.jorncruijsen.guildwars.service.domain.World;
import nl.jorncruijsen.guildwars.service.domain.World.TYPE;
import nl.jorncruijsen.guildwars.service.domain.WvWMatch;
import nl.jorncruijsen.guildwars.service.json.JSONWorldName;
import nl.jorncruijsen.guildwars.service.json.JSONWvWMatch;
import nl.jorncruijsen.guildwars.service.json.JSONWvWMatchInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

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
    System.out.println("Retrieving world names.");
    Map<Integer, JSONWorldName> worldNames = retrieveWorldNames();

    System.out.println("Retrieving match overview.");
    Map<String, JSONWvWMatch> matchOverview = retrieveMatchOverview();

    // Stitch world info to match info
    stitch(worldNames, matchOverview);

    System.out.println("Retrieving detailed match info.");
    for (WvWMatch m : matches.values()) {
      retrieveMatchInfo(m);
    }
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
    JSONWvWMatchInfo obj = gson.fromJson(parser.parse(content).getAsJsonObject(), JSONWvWMatchInfo.class);
    int[] scores = obj.getScores();
    
    match.getWorld(TYPE.RED).setScore(scores[0]);
    match.getWorld(TYPE.GREEN).setScore(scores[1]);
    match.getWorld(TYPE.BLUE).setScore(scores[2]);

    System.out.println(obj);
  }

  private void stitch(Map<Integer, JSONWorldName> worldNames, Map<String, JSONWvWMatch> matchOverview) {
    for (JSONWvWMatch jsonMatch : matchOverview.values()) {
      WvWMatch match = new WvWMatch();
      match.setId(jsonMatch.getWvwMatchId());
      match.addWorld(createWorld(TYPE.GREEN, jsonMatch.getGreenWorldId(), match, worldNames));
      match.addWorld(createWorld(TYPE.BLUE, jsonMatch.getBlueWorldId(), match, worldNames));
      match.addWorld(createWorld(TYPE.RED, jsonMatch.getRedWorldId(), match, worldNames));

      getMatches().put(match.getId(), match);
    }
  }

  private World createWorld(TYPE type, int worldId, WvWMatch match, Map<Integer, JSONWorldName> worldNames) {
    World world = new World();
    world.setId(worldId);
    world.setType(type);
    world.setName(worldNames.get(worldId).getName());
    world.setMatch(match);

    getWorlds().put(worldId, world);

    return world;
  }

  public Map<String, WvWMatch> getMatches() {
    return matches;
  }

  public Map<Integer, World> getWorlds() {
    return worlds;
  }
}
