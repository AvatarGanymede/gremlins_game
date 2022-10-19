package gremlins.gameobjects;

import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Renderer;

import static gremlins.gameutils.GameConst.*;

public class Door extends GameObject{
    public Door(int x, int y) {
        super(x, y);
        type = GO_TYPE.DOOR;
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
