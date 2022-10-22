package gremlins.gameobjects;

import gremlins.gameutils.CollisionProxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoneWallTest {
    StoneWall stoneWall;
    @BeforeEach
    void setup(){
        CollisionProxy.Instance().reset();
    }

    @Test
    void destroy() {
        stoneWall = new StoneWall(0, 0);
        stoneWall.destroy();
        assertTrue(CollisionProxy.Instance().checkCollision(0, 0));
        assertEquals(null, CollisionProxy.Instance().m_Go2MapIndex.get(stoneWall));
        stoneWall = null;
    }
}