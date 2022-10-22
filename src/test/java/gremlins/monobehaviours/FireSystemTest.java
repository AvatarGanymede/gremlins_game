package gremlins.monobehaviours;

import gremlins.Game;
import gremlins.gameobjects.BrickWall;
import gremlins.gameobjects.Gremlin;
import gremlins.gameobjects.Player;
import gremlins.gameobjects.projectiles.FireBall;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameConst;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static gremlins.gameutils.GameConst.*;
import static gremlins.gameutils.GameConst.FRAME_TICK;
import static org.junit.jupiter.api.Assertions.*;

class FireSystemTest {
    Player player;
    Gremlin gremlin;
    BrickWall brickWall;
    Game game;
    FireSystem fireSys1;
    FireSystem fireSys2;
    FireSystem fireSys3;

    @BeforeEach
    void setUp() {
        game = new Game();
        player = new Player(0, 0);
        gremlin = new Gremlin(0, 0);
        brickWall = new BrickWall(0, 0);
        fireSys1 = new FireSystem(player);
        fireSys2 = new FireSystem(gremlin);
        fireSys3 = new FireSystem(brickWall);
    }

    @AfterEach
    void tearDown() {
        game = null;
        player = null;
        gremlin = null;
        fireSys1 = null;
        fireSys2 = null;
    }

    @RepeatedTest(100)
    void onUpdate() {
        Movement movement = (Movement) gremlin.getMono(MOVEMENT);
        movement.move.set(CollisionProxy.Instance().calcGremlinMove(gremlin, movement.move));
        TIME_STAMP = TIME_STAMP.add(BigDecimal.valueOf(DELTA_TIME));
        fireSys1.OnUpdate();
        fireSys2.OnUpdate();
        fireSys3.OnUpdate();
        FRAME_TICK = FRAME_TICK.add(BigInteger.ONE);
    }
    @Test
    void onUpdateCorner() {
        Movement movement = (Movement) gremlin.getMono(MOVEMENT);
        movement.move.set(ZERO_VECTOR);
        fireSys2.nextShootTime = BigDecimal.valueOf(0);
        TIME_STAMP = TIME_STAMP.add(BigDecimal.valueOf(DELTA_TIME));
        fireSys1.OnUpdate();
        fireSys2.OnUpdate();
        fireSys3.OnUpdate();
        FRAME_TICK = FRAME_TICK.add(BigInteger.ONE);
    }

    @Test
    void keyPressed() {
        TIME_STAMP = BigDecimal.valueOf(0);
        fireSys1.nextShootTime = BigDecimal.valueOf(1);
        fireSys1.keyPressed(FIRE_KEY-1);
        fireSys2.keyPressed(FIRE_KEY-1);
        fireSys3.keyPressed(FIRE_KEY-1);

        TIME_STAMP = BigDecimal.valueOf(1);
        fireSys1.nextShootTime = BigDecimal.valueOf(0);
        fireSys1.keyPressed(FIRE_KEY-1);
        fireSys2.keyPressed(FIRE_KEY-1);
        fireSys3.keyPressed(FIRE_KEY-1);

        TIME_STAMP = BigDecimal.valueOf(0);
        fireSys1.nextShootTime = BigDecimal.valueOf(1);
        fireSys1.keyPressed(FIRE_KEY);
        fireSys2.keyPressed(FIRE_KEY);
        fireSys3.keyPressed(FIRE_KEY);

        TIME_STAMP = BigDecimal.valueOf(1);
        fireSys1.nextShootTime = BigDecimal.valueOf(0);
        fireSys1.keyPressed(FIRE_KEY);
        fireSys2.keyPressed(FIRE_KEY);
        fireSys3.keyPressed(FIRE_KEY);
    }

    @Test
    void keyReleased() {
        fireSys1.keyReleased(FIRE_KEY-1);
        fireSys1.keyReleased(FIRE_KEY);
        TIME_STAMP = BigDecimal.valueOf(0);
        fireSys1.keyReleased(FIRE_KEY);
    }
}