package gremlins.levels;

import gremlins.Game;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.textlevels.GameOverLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.File;

import static gremlins.gameutils.GameConst.*;
import static org.junit.jupiter.api.Assertions.*;

class GameLevelTest {
    @BeforeEach
    void setup(){
        GameLevel.levelIndex = 0;
        GameLevel.loadSuccess = false;
        GameLevel.win = false;
    }
    @AfterEach
    void teardown(){
        GameLevel.levelIndex = 0;
        GameLevel.loadSuccess = false;
        GameLevel.win = false;
    }

    @Test
    void loadLevelNullCase() {
        GameProxy.Instance().gameRef = null;
        GameLevel level = new GameLevel();
    }
    @Test
    void loadLevelBadCase1(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig1.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
    @Test
    void loadLevelBadCase2(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig2.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
    @Test
    void loadLevelBadCase3(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig3.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }

    @Test
    void loadLevelBadCase4(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig4.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
    @Test
    void loadLevelBadCase5(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig5.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
    @Test
    void loadLevelBadCase6(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig7.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        GameProxy.Instance().gameRef.level.Update();
        assertFalse(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
    @Test
    void loadLevelBadCasesWithGoodCase(){
        CONFIG = PApplet.loadJSONObject(new File("testcases/testconfig6.json"));
        GameProxy.Instance().gameRef = new Game();
        GameProxy.Instance().gameRef.level = new GameLevel();
        assertTrue(GameLevel.loadSuccess);

        CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
    }
}
