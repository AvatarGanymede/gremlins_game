package gremlins.monobehaviours;

import gremlins.Game;
import gremlins.gameobjects.*;
import gremlins.gameobjects.projectiles.FireBall;
import gremlins.gameobjects.projectiles.Slime;
import gremlins.gameutils.GameProxy;
import gremlins.gameutils.GameUtils;
import gremlins.levels.GameLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

import static gremlins.gameutils.GameConst.*;

import static org.junit.jupiter.api.Assertions.*;

class RendererTest {
    class TestClass extends GameObject{
        public TestClass(int x, int y) {
            super(x, y);
            type = GO_TYPE.TEST;
            InitMonos();
        }

        @Override
        protected void InitMonos() {
            m_Monos.put(RENDERER, new Renderer(this));
        }

        @Override
        public void destroy() {

        }
    }
    Game game;
    ArrayList<GameObject> entities;
    @BeforeEach
    void setup(){
        game = new Game();
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
    void teardown(){
        game = null;
        entities.clear();
        entities = null;
    }
    @Test
    void specialCase(){
        TestClass test = new TestClass(0, 0);
    }
    @RepeatedTest(5)
    void onBrickDestroyed() {
        BrickWall brickWall = (BrickWall) entities.get(2);
        Renderer renderer = (Renderer) brickWall.getMono(RENDERER);
        renderer.beDestroyed = true;
        renderer.OnUpdate();
        if(FRAME_TICK.equals(BigInteger.valueOf(4))){
            assertEquals(renderer.pathIndex, 1);
        }
        FRAME_TICK = FRAME_TICK.add(BigInteger.ONE);
    }
    @Test
    void specialTest(){
        TestClass test = new TestClass(0, 0);
        Renderer renderer = (Renderer) test.getMono(RENDERER);
        renderer.OnUpdate();
    }

    @Test
    void nonPlayerKeyPressed() {
        Gremlin gremlin = (Gremlin) entities.get(1);
        Renderer renderer = (Renderer) gremlin.getMono(RENDERER);
        for(int i = 0; i < 255; i++){
            renderer.keyPressed(i);
        }
    }
    @Test
    void playerKeyPressed(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        for(int i = 0; i < 255; i++){
            renderer.keyPressed(i);
            switch (i){
                case LEFT_KEY, RIGHT_KEY, UP_KEY, DOWN_KEY -> assertEquals(renderer.pathIndex, PLAYER_KEY2INDEX.get(i));
            }
        }
    }

    @Test
    void keyReleasedCase1() {
        Gremlin gremlin = (Gremlin) entities.get(1);
        Renderer renderer = (Renderer) gremlin.getMono(RENDERER);
        for(int i = 0; i < 255; i++){
            renderer.keyReleased(i);
        }
    }
    @Test
    void keyReleasedCase2(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        for(int i = 0; i < 255; i++){
            renderer.keyReleased(i);
        }
    }
    @Test
    void keyReleasedCase3(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        renderer.keyPressed(UP_KEY);
        renderer.keyReleased(UP_KEY);
        assertEquals(renderer.pathIndex, PLAYER_KEY2INDEX.get(UP_KEY));
    }
    @Test
    void keyReleasedCase4(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        renderer.keyPressed(UP_KEY);
        renderer.keyPressed(DOWN_KEY);
        renderer.keyReleased(UP_KEY);
        assertEquals(renderer.pathIndex, PLAYER_KEY2INDEX.get(DOWN_KEY));
    }
    @Test
    void keyReleasedCase5(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        renderer.keyPressed(UP_KEY);
        renderer.keyReleased(DOWN_KEY);
        assertEquals(renderer.pathIndex, PLAYER_KEY2INDEX.get(UP_KEY));
    }
    @RepeatedTest(100)
    void keyReleasedRandom(){
        Player player = (Player) entities.get(0);
        Renderer renderer = (Renderer) player.getMono(RENDERER);
        boolean isPressed = GameUtils.random.nextBoolean();
        int keyIndex = GameUtils.random.nextInt(4);
        if(isPressed) {
            renderer.keyPressed(PLAYER_INDEX2KEY.get(keyIndex));
            assertEquals(renderer.pathIndex, keyIndex);
        }else {
            renderer.keyReleased(PLAYER_INDEX2KEY.get(keyIndex));
        }
    }
}