package gremlins.monobehaviours;

import gremlins.Game;
import gremlins.gameobjects.*;
import gremlins.gameutils.GameProxy;
import gremlins.levels.GameLevel;
import gremlins.levels.textlevels.WelcomeLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static gremlins.gameutils.GameConst.*;

class CollisionTest {
    Game game;
    ArrayList<GameObject> entities;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.level = new GameLevel();
        GameProxy.Instance().gameRef = game;
        entities = new ArrayList<>();

        entities.add(new Player(0, 0));
        entities.add(new Gremlin(1, 1));
        entities.add(new BrickWall(2, 2));
        entities.add(new Door(3, 3));
        entities.add(new StoneWall(4, 4));
    }

    @AfterEach
    void tearDown() {
        game = null;
        entities.clear();
        entities = null;
    }

    @Test
    void onHit() {
        for(GameObject go : entities){
            Collision collision = (Collision) go.getMono(COLLISION);
            for(GameObject e : entities){
                if(go.equals(e)){
                    continue;
                }
                collision.onHit(e);
            }
        }
    }

    @Test
    void onHitCorner() {
        game.level = new WelcomeLevel();
        for(GameObject go : entities){
            Collision collision = (Collision) go.getMono(COLLISION);
            for(GameObject e : entities){
                if(go.equals(e)){
                    continue;
                }
                collision.onHit(e);
            }
        }
    }

    @Test
    void onPlayerHit(){
        Player player = (Player) entities.get(0);
        
    }

    @Test
    void onUpdate() {
    }

    @Test
    void keyPressed() {
    }

    @Test
    void keyReleased() {
    }
}