package gremlins;

import gremlins.gameobjects.*;
import gremlins.gameutils.GameProxy;
import processing.core.PApplet;

import java.math.BigDecimal;
import java.math.BigInteger;

import static gremlins.gameutils.GameConst.*;

public class Game extends PApplet {
    //public static final Random randomGenerator = new Random();

    public Player player;
    public StoneWall stoneWall;
    public Gremlin gremlin;
    public BrickWall brickWall;

    public Game() { }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);
        PLAYER_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(0).getDouble("wizard_cooldown");
        GREMLIN_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(0).getDouble("enemy_cooldown");

        GameProxy.Instance().gameRef = this;
        this.player = new Player(0, 0);
        this.stoneWall = new StoneWall(100, 100);
        this.gremlin = new Gremlin(80, 100);
        this.brickWall = new BrickWall(120, 120);
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if(!GameProxy.Instance().registeredKey.containsKey(keyCode)){
            return;
        }
        if(GameProxy.Instance().registeredKey.get(keyCode)){
            return;
        }
        GameProxy.Instance().registeredKey.put(keyCode, true);
        player.keyPressed(keyCode);
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
        if(!GameProxy.Instance().registeredKey.containsKey(keyCode)){
            return;
        }
        GameProxy.Instance().registeredKey.put(keyCode, false);
        player.keyReleased(keyCode);
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(255);
        TIME_STAMP = TIME_STAMP.add(BigDecimal.valueOf(DELTA_TIME));
        player.Update();
        stoneWall.Update();
        gremlin.Update();
        brickWall.Update();
        for(int hashCode : GameProxy.Instance().Entities.keySet()){
            GameProxy.Instance().Entities.get(hashCode).Update();
        }
        FRAME_TICK = FRAME_TICK.add(BigInteger.ONE);
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.Game");
    }
}
