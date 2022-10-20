package gremlins;


import gremlins.gameobjects.BrickWall;
import gremlins.gameobjects.GameObject;
import org.junit.jupiter.api.Test;

public class BrickWallTest {

    @Test
    public void UpdateTest() {
        BrickWall brickWall = new BrickWall(0, 0);
        brickWall.Update();
    }
}
