package gremlins.gameutils;

import gremlins.Game;
import gremlins.gameobjects.BrickWall;
import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Player;
import gremlins.monobehaviours.Movement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;
import static org.junit.jupiter.api.Assertions.*;

class CollisionProxyTest {
    class TestGo extends GameObject{

        public TestGo(int x, int y) {
            super(x, y);
            type = GO_TYPE.TEST;
        }

        @Override
        protected void InitMonos() {

        }

        @Override
        public void destroy() {

        }
    }
    class TestGo1 extends GameObject{

        public TestGo1(int x, int y) {
            super(x, y);
            type = GO_TYPE.BRICKWALL;
        }

        @Override
        protected void InitMonos() {

        }

        @Override
        public void destroy() {

        }
    }
    class TestGo2 extends GameObject{

        public TestGo2(int x, int y) {
            super(x, y);
            type = GO_TYPE.STONEWALL;
        }

        @Override
        protected void InitMonos() {

        }

        @Override
        public void destroy() {

        }
    }
    class TestGo3 extends GameObject{

        public TestGo3(int x, int y) {
            super(x, y);
            type = GO_TYPE.GREMLINS;
        }

        @Override
        protected void InitMonos() {
            m_Monos.put(MOVEMENT, new Movement(this));
        }

        @Override
        public void destroy() {

        }
    }
    @BeforeEach
    void setup(){
        CollisionProxy.Instance().reset();
    }
    @AfterEach
    void teardown(){
        CollisionProxy.Instance().reset();
    }
    @Test
    void getMapIndex() {
        PVector pos = new PVector(0, 0);
        PVector rst = CollisionProxy.getMapIndex(pos);
        assertEquals(0, rst.x);
        assertEquals(0, rst.y);

        pos.set(0.1f, 0.1f);
        rst.set(CollisionProxy.getMapIndex(pos));
        assertEquals(0, rst.x);
        assertEquals(0, rst.y);

        pos.set(5f, 0.5f);
        rst.set(CollisionProxy.getMapIndex(pos));
        assertEquals(0, rst.x);
        assertEquals(0, rst.y);

        pos.set(TILE_SIZE, TILE_SIZE);
        rst.set(CollisionProxy.getMapIndex(pos));
        assertEquals(1, rst.x);
        assertEquals(1, rst.y);
    }

    @Test
    void registerCollisionCase1() {
        TestGo go = new TestGo(0, 0);
        CollisionProxy.Instance().registerCollision(go);
        go.position.set(100, 100);
        CollisionProxy.Instance().registerCollision(go);
        assertTrue(CollisionProxy.Instance().checkCollision(0, 0));
        CollisionProxy.Instance().unregisterCollision(go);
    }

    @Test
    void registerCollisionCase2() {
        TestGo go = new TestGo(TILE_SIZE, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(go);
        TestGo1 go1 = new TestGo1(0, 0);
        assertEquals(0, CollisionProxy.Instance().registerCollision(go1).size());
        go1.position.set(1, 1);
        assertEquals(1, CollisionProxy.Instance().registerCollision(go1).size());
        go1.position.set(1, TILE_SIZE);
        assertEquals(1, CollisionProxy.Instance().registerCollision(go1).size());
        TestGo2 go2 = new TestGo2(2, 2);
        assertEquals(2, CollisionProxy.Instance().registerCollision(go2).size());
        CollisionProxy.Instance().unregisterCollision(go);
        CollisionProxy.Instance().unregisterCollision(go1);
        CollisionProxy.Instance().unregisterCollision(go2);
    }

    @Test
    void unregisterCollision() {
        TestGo go = new TestGo(TILE_SIZE, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(go);
        CollisionProxy.Instance().unregisterCollision(go);
        assertTrue(CollisionProxy.Instance().checkCollision(1, 1));
    }

    @Test
    void checkCollision() {
        TestGo go = new TestGo(TILE_SIZE, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(go);
        assertFalse(CollisionProxy.Instance().checkCollision(1, 1));
        CollisionProxy.Instance().unregisterCollision(go);
        assertTrue(CollisionProxy.Instance().checkCollision(1, 1));
    }

    @Test
    void calcGremlinMove() {
        TestGo3 gremlin = new TestGo3(0, 0);
        CollisionProxy.Instance().calcGremlinMove(gremlin, ZERO_VECTOR);
        gremlin.InitMonos();
        PVector move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
        assertEquals(1f, move.x);
        assertEquals(0f, move.y);

        TestGo2 wall = new TestGo2(TILE_SIZE, 0);
        CollisionProxy.Instance().registerCollision(wall);

        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(1, 0)));
        assertEquals(0f, move.x);
        assertEquals(1f, move.y);

        gremlin.position.set(TILE_SIZE, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(gremlin);
        TestGo1 brick = new TestGo1(TILE_SIZE, TILE_SIZE*2);
        CollisionProxy.Instance().registerCollision(brick);
        TestGo1 brick1 = new TestGo1(TILE_SIZE*2, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(brick1);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(1, 0)));
        assertEquals(-1f, move.x);
        assertEquals(0f, move.y);
    }

    @Test
    void calcGremlinMoveCornerCase() {
        TestGo3 gremlin = new TestGo3(0, 0);
        CollisionProxy.Instance().calcGremlinMove(gremlin, ZERO_VECTOR);
        gremlin.InitMonos();
        PVector move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
        assertEquals(new PVector(1, 0), move);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(-1, 0)));
        assertEquals(new PVector(0, 1), move);

        gremlin.position.set((TILE_NUM_X-1)*TILE_SIZE, 0);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(1, 0)));
        assertEquals(new PVector(0, 1), move);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)));
        assertEquals(new PVector(-1, 0), move);

        gremlin.position.set(0, (TILE_NUM_Y-1)*TILE_SIZE);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(-1, 0)));
        assertEquals(new PVector(0, -1), move);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, 1)));
        assertEquals(new PVector(1, 0), move);

        gremlin.position.set((TILE_NUM_X-1)*TILE_SIZE, (TILE_NUM_Y-1)*TILE_SIZE);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(1, 0)));
        assertEquals(new PVector(0, -1), move);
        move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, 1)));
        assertEquals(new PVector(-1, 0), move);
    }

    @Test
    void calcGremlinMoveNormalCase() {
        TestGo3 gremlin = new TestGo3(TILE_SIZE, TILE_SIZE);
        CollisionProxy.Instance().calcGremlinMove(gremlin, ZERO_VECTOR);
        gremlin.InitMonos();
        PVector move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
        assertNotEquals(move,  new PVector(0, -1));

        gremlin.position.set(TILE_SIZE*(TILE_NUM_X-2), TILE_SIZE);
        move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
        assertNotEquals(move, new PVector(0, -1));

        gremlin.position.set(TILE_SIZE*(TILE_NUM_X-3), TILE_SIZE);
        move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
        assertNotEquals(move, new PVector(0, -1));

        gremlin.position.set(TILE_SIZE, TILE_SIZE);
        TestGo3 gremlin1 = new TestGo3(TILE_SIZE*2, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(gremlin1);
        TestGo3 gremlin2 = new TestGo3(TILE_SIZE*2, TILE_SIZE);
        CollisionProxy.Instance().registerCollision(gremlin2);
        move = CollisionProxy.Instance().calcGremlinMove(gremlin, new PVector(0, -1)).copy();
    }
}