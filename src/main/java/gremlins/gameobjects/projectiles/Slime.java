package gremlins.gameobjects.projectiles;

import gremlins.gameutils.GameConst;
import gremlins.gameutils.GameProxy;
import processing.core.PVector;

public class Slime extends Projectile {

    public Slime(int x, int y) {
        super(x, y);
        type = GameConst.GO_TYPE.SLIME;
        InitMonos();
    }

    public static void create(int x, int y, PVector move){
        Slime slime = new Slime(x, y);
        slime.setMove(move);
        GameProxy.Instance().entities.put(slime.hashCode(), slime);
    }
}
