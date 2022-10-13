package gremlins.gameutils;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;

import java.io.File;

public class GameConst {
    public enum DIRECTION{
        LEFT,
        RIGHT,
        UP,
        DOWN;
    }
    public static PVector[] MOVE = {new PVector(-1, 0), new PVector(1, 0), new PVector(0, -1), new PVector(0, 1)};
    public enum GO_TYPE{
        PLAYER,
        GREMLINS,
        FIREBALL;
    }
    public static int PLAYER_VELOCITY = 2;
    public static int FIREBALL_VELOCITY = 4;
    public static int GREMLINS_VELOCITY = 4;

    public static String CONFIG_PATH = "config.json";
    public static JSONObject CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));
}
