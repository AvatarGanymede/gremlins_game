package gremlins.gameobjects;

import gremlins.gameutils.CollisionProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GremlinTest {
    Gremlin gremlin;

    @BeforeEach
    void setup(){
        CollisionProxy.Instance().reset();
    }

    @Test
    void destroy() {
        gremlin = new Gremlin(0, 0);
        gremlin.destroy();
        assertTrue(CollisionProxy.Instance().checkCollision(0, 0));
    }
}