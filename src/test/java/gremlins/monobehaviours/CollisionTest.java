package gremlins.monobehaviours;

import gremlins.Game;
import gremlins.gameobjects.*;
import gremlins.gameobjects.projectiles.FireBall;
import gremlins.gameobjects.projectiles.Slime;
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
        entities.add(new BrickWall(0, 0));
        entities.add(new Door(3, 3));
        entities.add(new StoneWall(4, 4));
        entities.add(new Slime(5, 5));
        entities.add(new FireBall(6, 6));
    }

    @AfterEach
    void tearDown() {
        game = null;
        entities.clear();
        entities = null;
    }

    @Test
    void onHitAndUpdate() {
        for(GameObject go : entities){
            Collision collision = (Collision) go.getMono(COLLISION);
            for(GameObject e : entities){
                if(go.equals(e)){
                    continue;
                }
                collision.onHit(e);
            }
            collision.OnUpdate();
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
        Movement movement = (Movement) player.getMono(MOVEMENT);
        movement.prevPosition.set(1, 1);
        Collision collision = (Collision) player.getMono(COLLISION);
        collision.onHit(entities.get(2));
        collision.onHit(entities.get(4));
        BrickWall brickWall = new BrickWall(100, 100);
        collision.onHit(brickWall);
        brickWall = null;
    }

    @Test
    void slimeHitSlime(){
        Slime slime1 = (Slime) entities.get(5);
        Slime slime2 = new Slime(0, 0);
        Collision collision = (Collision) slime1.getMono(COLLISION);
        collision.onHit(slime2);
        slime2 = null;
    }

    @Test
    void onUpdate() {
        for(GameObject go : entities){
            Collision collision = (Collision) go.getMono(COLLISION);
            collision.OnUpdate();
        }
    }

    @Test
    void onUpdateCornerCase(){
        class TestGo extends GameObject{

            public TestGo(int x, int y){
                super(x, y);
                InitMonos();
            }
            @Override
            protected void InitMonos() {
                m_Monos.put(COLLISION, new Collision(this, false));
            }

            @Override
            public void destroy() {

            }
        }
        TestGo go = new TestGo(0, 0);
        Collision collision = (Collision) go.getMono(COLLISION);
        collision.OnUpdate();
    }

    @Test
    void keyPressed() {
        Player player = (Player) entities.get(0);
        Collision collision = (Collision) player.getMono(COLLISION);
        collision.keyPressed(0);
    }

    @Test
    void keyReleased() {
        Player player = (Player) entities.get(0);
        Collision collision = (Collision) player.getMono(COLLISION);
        collision.keyReleased(0);
    }
}