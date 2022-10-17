package gremlins.gameutils;

import processing.core.PVector;

import java.util.Map;

public class GameConst {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;

    public static final int TILE_NUM_X = 36;
    public static final int TILE_NUM_Y = 33;
    public static final int TILE_SIZE = 20;


    //public static final int SPRITE_SIZE = 20;
    //public static final int BOTTOM_BAR = 60;

    public static final int FPS = 60;
    public static final PVector ZERO_VECTOR = new PVector(0, 0);

    public static final int LEFT_KEY = 37;
    public static final int UP_KEY = 38;
    public static final int RIGHT_KEY = 39;
    public static final int DOWN_KEY = 40;
    public static final int FIRE_KEY = 32;
    public static final Map<Integer, PVector> KEY2DIRECTION = Map.of(
            LEFT_KEY, new PVector(-1, 0),
            RIGHT_KEY, new PVector(1, 0),
            UP_KEY, new PVector(0, -1),
            DOWN_KEY, new PVector(0, 1)
    );
    public enum GO_TYPE{
        PLAYER,
        GREMLINS,
        FIREBALL,
        BRICKWALL,
        STONEWALL
    }
    public static final int PLAYER_VELOCITY = 2;
    public static final int FIREBALL_VELOCITY = 4;
    public static final int GREMLINS_VELOCITY = 4;

    //public static final String CONFIG_PATH = "config.json";
    //public static final JSONObject CONFIG = PApplet.loadJSONObject(new File(CONFIG_PATH));

    public static final Map<Integer, Integer> PLAYER_KEY2INDEX = Map.of(LEFT_KEY, 0, RIGHT_KEY, 1, UP_KEY, 2, DOWN_KEY, 3);
    public static final Map<Integer, Integer> PLAYER_INDEX2KEY = Map.of(0, LEFT_KEY, 1, RIGHT_KEY, 2, UP_KEY, 3, DOWN_KEY);
    //
    //    //public static final String[] BRICK_WALL_PATHS = {"brickwall.png", "brickwall_destroyed0.png", "brickwall_destroyed0.png", "brickwall_destroyed0.png", "brickwall_destroyed0.png"};
    public static final String[] FIREBALL_PATHS = {"fireball.png"};
    public static final String[] GREMLIN_PATHS = {"gremlin.png"};
    //public static final String[] SLIME_PATHS = {"slime.png"};
    public static final String[] STONE_WALL_PATHS = {"stonewall.png"};
    public static final String[] WIZARD_PATHS = {"wizard0.png", "wizard1.png", "wizard2.png", "wizard3.png"};

    public static final String MOVEMENT = "Movement";
    public static final String COLLISION = "Collision";
    public static final String RENDERER = "Renderer";
    public static final String FIRE_SYSTEM = "FireSystem";
}
