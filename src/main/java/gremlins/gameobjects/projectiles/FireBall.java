package gremlins.gameobjects.projectiles;

import gremlins.gameutils.GameProxy;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class FireBall extends Projectile {
    public FireBall(int x, int y) {
        super(x, y);
        type = GO_TYPE.FIREBALL;
        InitMonos();
    }

    public static void create(int x, int y, PVector move){
        FireBall fireBall = new FireBall(x, y);
        fireBall.setMove(move);
        GameProxy.Instance().entities.put(fireBall.hashCode(), fireBall);
    }
}
