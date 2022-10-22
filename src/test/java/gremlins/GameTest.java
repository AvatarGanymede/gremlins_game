package gremlins;

import gremlins.gameutils.GameProxy;
import gremlins.levels.textlevels.WelcomeLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        GameProxy.Instance().gameRef = game;
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    @Test
    void keyPressed() {
        game.isLoaded = false;
        game.level = null;
        game.keyPressed();

        game.level = new WelcomeLevel();
        game.isLoaded = false;
        game.keyPressed();

        game.level = null;
        game.isLoaded = true;
        game.keyPressed();

        game.level = new WelcomeLevel();
        game.isLoaded = true;
        game.keyPressed();
    }

    @Test
    void keyReleased() {
        game.isLoaded = false;
        game.level = null;
        game.keyReleased();

        game.level = new WelcomeLevel();
        game.isLoaded = false;
        game.keyReleased();

        game.level = null;
        game.isLoaded = true;
        game.keyReleased();

        game.level = new WelcomeLevel();
        game.isLoaded = true;
        game.keyReleased();
    }
}