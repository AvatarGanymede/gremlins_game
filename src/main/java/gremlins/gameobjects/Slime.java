package gremlins.gameobjects;

import gremlins.gameutils.GameConst;
import gremlins.gameutils.GameProxy;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Slime extends AttackEntity{

    public Slime(int x, int y) {
        super(x, y);
        type = GameConst.GO_TYPE.SLIME;
        InitMonos();
    }

    public static void create(int x, int y, PVector move){
        Slime slime = new Slime(x, y);
        slime.setMove(move);
        GameProxy.Instance().Entities.put(slime.hashCode(), slime);
    }
}
