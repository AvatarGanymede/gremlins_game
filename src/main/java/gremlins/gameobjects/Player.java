package gremlins.gameobjects;

import gremlins.gameutils.GameConst;

public class Player extends GameObject{
    public Player(int x, int y){
        super(x, y);
        type = GameConst.GO_TYPE.PLAYER;
        InitMonos();
    }
    @Override
    protected void InitMonos(){

    }
}
