package gremlins.gameobjects;

import gremlins.Game;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.textlevels.LoseLevel;
import gremlins.levels.textlevels.WelcomeLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static gremlins.gameutils.GameConst.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    @BeforeEach
    void setUp() {
        CollisionProxy.Instance().reset();
    }

    @Test
    void beKilled() {
        Game game = new Game();
        GameProxy.Instance().gameRef = game;
        GameProxy.Instance().gameRef.level = new WelcomeLevel();
        player = new Player(0, 0);
        player.beKilled();
        assertEquals(MAX_PLAYER_LIVES-1, player.lives);
        assertEquals(MAX_PLAYER_LIVES-1, player.lives);

        FRAME_TICK = FRAME_TICK.add(BigInteger.valueOf(FPS+1));
        player.beKilled();
        assertEquals(MAX_PLAYER_LIVES-2, player.lives);

        FRAME_TICK = FRAME_TICK.add(BigInteger.valueOf(FPS+2));
        player.beKilled();
        assertEquals(MAX_PLAYER_LIVES-3, player.lives);
        FRAME_TICK = FRAME_TICK.add(BigInteger.valueOf(FPS+3));
        player.beKilled();
        assertEquals(MAX_PLAYER_LIVES-4, player.lives);

        LoseLevel loseLevel = (LoseLevel) GameProxy.Instance().gameRef.level;
    }

    @Test
    void onUpdateTest() {
        player = new Player(0, 0);
        player.Update();
    }
}