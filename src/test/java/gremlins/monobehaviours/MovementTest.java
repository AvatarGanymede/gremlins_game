package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Gremlin;
import gremlins.gameobjects.Player;
import gremlins.gameobjects.projectiles.FireBall;
import gremlins.gameobjects.projectiles.Slime;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameConst;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static gremlins.gameutils.GameConst.*;
import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
    class TestGo extends GameObject{

        public TestGo(int x, int y) {
            super(x, y);
            type = GameConst.GO_TYPE.TEST;
        }

        @Override
        protected void InitMonos() {

        }

        @Override
        public void destroy() {

        }
    }
    Movement movement;

    @BeforeEach
    void setup(){
        CollisionProxy.Instance().reset();
    }
    @AfterEach
    void teardown(){
        CollisionProxy.Instance().reset();
    }
    @Test
    void specialCtor(){
        movement = new Movement(new TestGo(0, 0));
    }

    @Test
    void onUpdateBoarderCase() {
        Player player = new Player(0, 0);
        player.keyPressed(UP_KEY);
        player.keyPressed(LEFT_KEY);
        movement = (Movement) player.getMono(MOVEMENT);
        for(int i = 0; i < 50; i ++){
            movement.OnUpdate();
            assertEquals(player.position.x, 0);
            assertEquals(player.position.y, 0);
        }

        player = new Player(WIDTH-1, 0);
        player.keyPressed(UP_KEY);
        player.keyPressed(RIGHT_KEY);
        movement = (Movement) player.getMono(MOVEMENT);
        for(int i = 0; i < 50; i ++){
            movement.OnUpdate();
            assertEquals(player.position.x, WIDTH-1);
            assertEquals(player.position.y, 0);
        }

        player = new Player(0, TILE_SIZE*TILE_NUM_Y-1);
        player.keyPressed(DOWN_KEY);
        player.keyPressed(LEFT_KEY);
        movement = (Movement) player.getMono(MOVEMENT);
        for(int i = 0; i < 50; i ++){
            movement.OnUpdate();
            assertEquals(player.position.x, 0);
            assertEquals(player.position.y, TILE_SIZE*TILE_NUM_Y-1);
        }
        player = new Player(WIDTH-1, TILE_SIZE*TILE_NUM_Y-1);
        player.keyPressed(DOWN_KEY);
        player.keyPressed(RIGHT_KEY);
        movement = (Movement) player.getMono(MOVEMENT);
        for(int i = 0; i < 50; i ++){
            movement.OnUpdate();
            assertEquals(player.position.x, WIDTH-1);
            assertEquals(player.position.y, TILE_SIZE*TILE_NUM_Y-1);
        }
    }
    @Test
    void onUpdateStopCase1(){
        Player player = new Player(0, 0);
        movement = (Movement) player.getMono(MOVEMENT);
        player.keyPressed(RIGHT_KEY);
        movement.OnUpdate();
        player.keyReleased(RIGHT_KEY);

        for(int i = 0; i < 50; i++){
            movement.OnUpdate();
        }
        assertEquals(TILE_SIZE, player.position.x);
    }

    @Test
    void onUpdateStopCase2(){
        Player player = new Player(0, 0);
        movement = (Movement) player.getMono(MOVEMENT);
        player.keyPressed(RIGHT_KEY);
        player.keyPressed(DOWN_KEY);
        movement.OnUpdate();
        player.keyReleased(RIGHT_KEY);
        player.keyReleased(DOWN_KEY);

        for(int i = 0; i < 50; i++){
            movement.OnUpdate();
        }
        assertEquals(TILE_SIZE, player.position.x);
        assertEquals(TILE_SIZE, player.position.y);
    }

    @Test
    void onUpdateAbnormalSpawnPos(){
        Player player = new Player(1, 1);
        movement = (Movement) player.getMono(MOVEMENT);
        for(int i = 0; i < 50; i++){
            movement.OnUpdate();
        }
    }

    @Test
    void keyTest() {
        Player player = new Player(0, 0);
        movement = new Movement(player);
        movement.keyPressed(0);
        assertEquals(0, movement.move.x);
        assertEquals(0, movement.move.y);

        movement.keyPressed(UP_KEY);
        assertEquals(-1, movement.move.y);

        movement.keyPressed(DOWN_KEY);
        assertEquals(0, movement.move.y);

        movement.keyReleased(UP_KEY);
        assertEquals(1, movement.move.y);
        movement.keyReleased(UP_KEY);
        assertEquals(1, movement.move.y);

        movement.keyPressed(LEFT_KEY);
        assertEquals(-1, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyPressed(RIGHT_KEY);
        assertEquals(0, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyReleased(LEFT_KEY);
        assertEquals(1, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyReleased(LEFT_KEY);
        assertEquals(1, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyReleased(11);
        assertEquals(1, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyPressed(RIGHT_KEY);
        movement.keyPressed(DOWN_KEY);
        assertEquals(1, movement.move.x);
        assertEquals(1, movement.move.y);

        movement.keyReleased(RIGHT_KEY);
        movement.keyReleased(DOWN_KEY);
        assertEquals(0, movement.move.x);
        assertEquals(0, movement.move.y);
    }

    @Test
    void otherGoTest(){
        Gremlin gremlin = new Gremlin(0, 0);
        FireBall fireBall = new FireBall(0, 0);
        Slime slime = new Slime(0, 0);
        movement = (Movement) gremlin.getMono(MOVEMENT);
        movement.OnUpdate();
        movement = (Movement) fireBall.getMono(MOVEMENT);
        movement.OnUpdate();
        movement = (Movement) slime.getMono(MOVEMENT);
        movement.OnUpdate();
    }
}