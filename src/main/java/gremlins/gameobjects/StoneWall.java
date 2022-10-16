package gremlins.gameobjects;

import gremlins.gameutils.GameConst;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Renderer;

import static gremlins.gameutils.GameConst.*;

public class StoneWall extends GameObject {

    public StoneWall(int x, int y) {
        super(x, y);
        type = GameConst.GO_TYPE.STONEWALL;
        InitMonos();
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(COLLISION, new Collision(this, true));
        m_Monos.put(RENDERER, new Renderer(this));
    }

    @Override
    public void destroy() {

    }
}
