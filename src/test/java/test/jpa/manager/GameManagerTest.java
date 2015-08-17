package test.jpa.manager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.jpa.model.Game;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(Arquillian.class)
public class GameManagerTest {

    private static final String[] GAME_TITLES = {
            "Super Mario Brothers",
            "Mario Kart",
            "F-Zero"
    };

    @Inject
    GameManager gameManager;

    @Inject
    UserTransaction utx;

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive deployment = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "test.jpa")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(deployment.toString(true));
        return deployment;
    }

    private static void assertContainsAllGames(Collection<Game> retrievedGames) {
        Assert.assertEquals(GAME_TITLES.length, retrievedGames.size());
        final Set<String> retrievedGameTitles = new HashSet<String>();
        for (Game game : retrievedGames) {
            System.out.println("* " + game);
            retrievedGameTitles.add(game.getTitle());
        }
        Assert.assertTrue(retrievedGameTitles.containsAll(Arrays.asList(GAME_TITLES)));
    }

    @Before
    public void preparePersistenceTest() throws Exception {
        utx.begin();
        insertData();
    }

    private void insertData() throws Exception {
        System.out.println("Inserting records...");
        for (String title : GAME_TITLES) {
            Game game = new Game(title);
            gameManager.create(game);
            System.out.println("========>" + game.getId());
        }
    }

    @Test
    public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
        // given

        // when
        System.out.println("Fetching all entities ...");
        List<Game> games = gameManager.list();

        // then
        System.out.println("Found " + games.size() + " games (using JPQL):");
        assertContainsAllGames(games);
    }
}
