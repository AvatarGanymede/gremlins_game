package gremlins.gameobjects;

import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameConst;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Renderer;

import static gremlins.gameutils.GameConst.COLLISION;
import static gremlins.gameutils.GameConst.RENDERER;

public class BrickWall extends GameObject{

    public BrickWall(int x, int y) {
        super(x, y);
        type = GameConst.GO_TYPE.BRICKWALL;
        InitMonos();
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(COLLISION, new Collision(this, true));
        m_Monos.put(RENDERER, new Renderer(this));
    }

    @Override
    public void destroy() {
        CollisionProxy.Instance().unregisterCollision(this);
    }
}
