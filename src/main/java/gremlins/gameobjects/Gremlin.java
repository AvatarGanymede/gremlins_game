package gremlins.gameobjects;

import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;

import static gremlins.gameutils.GameConst.*;

public class Gremlin extends GameObject{

    public Gremlin(int x, int y) {
        super(x, y);
        type = GO_TYPE.GREMLINS;
        InitMonos();
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
    }

    @Override
    public void destroy() {

    }
}
