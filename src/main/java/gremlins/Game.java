package gremlins;

import gremlins.gameobjects.FireBall;
import gremlins.gameobjects.Gremlin;
import gremlins.gameobjects.Player;
import gremlins.gameobjects.StoneWall;
import gremlins.gameutils.GameProxy;
import processing.core.PApplet;

import static gremlins.gameutils.GameConst.*;

public class Game extends PApplet {
    //public static final Random randomGenerator = new Random();

    public Player player;
    public StoneWall stoneWall;
    public Gremlin gremlin;

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
        GameProxy.Instance().gameRef = this;
        this.player = new Player(0, 0);
        this.stoneWall = new StoneWall(100, 100);
        this.gremlin = new Gremlin(80, 100);

        //JSONObject conf = loadJSONObject(new File(CONFIG_PATH));
        //println(conf.getJSONArray("levels").getJSONObject(0).getDouble("wizard_cooldown"));
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
        player.Update();
        stoneWall.Update();
        gremlin.Update();
        for(int hashCode : GameProxy.Instance().FireBalls.keySet()){
            GameProxy.Instance().FireBalls.get(hashCode).Update();
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.Game");
    }
}
