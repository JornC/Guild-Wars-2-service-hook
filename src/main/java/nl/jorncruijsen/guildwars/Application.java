package nl.jorncruijsen.guildwars;

import nl.jorncruijsen.guildwars.service.GuildWarsServiceRetriever;
import nl.jorncruijsen.guildwars.service.domain.World;

public class Application {
  private final GuildWarsServiceRetriever retriever = GuildWarsServiceRetriever.I;

  private final int testId = 2007;

  public Application() {
    retriever.init();

    World world = retriever.getWorlds().get(testId);

    System.out.println("Test world [" + testId + "]:");
    System.out.println(world);
    System.out.println(world.getMatch());
  }

  public static void main(String... args) {
    new Application();
  }
}
