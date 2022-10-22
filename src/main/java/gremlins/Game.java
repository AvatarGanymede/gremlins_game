package gremlins;

import gremlins.gameutils.GameProxy;
import gremlins.levels.Level;
import gremlins.levels.textlevels.WelcomeLevel;
import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

import static gremlins.gameutils.GameConst.*;

public class Game extends PApplet {
    public static ArrayList<PImage> wizardImages = new ArrayList<>();
    public static ArrayList<PImage> fireballImages = new ArrayList<>();
    public static ArrayList<PImage> slimeImages = new ArrayList<>();
    public static ArrayList<PImage> gremlinImages = new ArrayList<>();
    public static ArrayList<PImage> stonewallImages = new ArrayList<>();
    public static ArrayList<PImage> brickWallImages = new ArrayList<>();
    public static ArrayList<PImage> doorImages = new ArrayList<>();
    public Level level;
    public boolean isLoaded;
    public Game() {
        isLoaded = false;
    }

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
        textAlign(CENTER);

        GameProxy.Instance().gameRef = this;
        GameProxy.Instance().titleFont = createFont(this.getClass().getResource(TITLE_FONT).getPath().replace("%20", " "), TITLE_SIZE, true);
        GameProxy.Instance().textFont = createFont(TEXT_FONT, TEXT_SIZE, false);

        for(String path : WIZARD_PATHS){
            try{
                wizardImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : FIREBALL_PATHS){
            try{
                fireballImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : SLIME_PATHS){
            try{
                slimeImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : GREMLIN_PATHS){
            try{
                gremlinImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : STONE_WALL_PATHS){
            try{
                stonewallImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : BRICK_WALL_PATHS){
            try{
                brickWallImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }
        for(String path : DOOR_PATHS){
            try{
                doorImages.add(loadImage(Objects.requireNonNull(this.getClass().getResource(path)).getPath().replace("%20", " ")));
            }catch (NullPointerException ignored) { }
        }

        level = new WelcomeLevel();
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if(level != null && isLoaded){
            level.keyPressed(keyCode);
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
        if(level != null && isLoaded){
            level.keyReleased(keyCode);
        }
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(BACKGROUND_COLOR[0], BACKGROUND_COLOR[1], BACKGROUND_COLOR[2]);
        if(level != null && isLoaded){
            TIME_STAMP = TIME_STAMP.add(BigDecimal.valueOf(DELTA_TIME));
            level.Update();
            FRAME_TICK = FRAME_TICK.add(BigInteger.ONE);
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.Game");
    }
}
