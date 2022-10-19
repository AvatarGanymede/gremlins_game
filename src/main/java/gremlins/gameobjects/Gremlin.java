package gremlins.gameobjects;

import gremlins.gameutils.CollisionProxy;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.FireSystem;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Gremlin extends GameObject{

    public Gremlin(int x, int y) {
        super(x, y);
        type = GO_TYPE.GREMLINS;
        InitMonos();
    }

    public PVector getDirection(){
        Movement movement = (Movement) m_Monos.get(MOVEMENT);
        if(movement == null){
            return null;
        }
        return movement.move;
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
        m_Monos.put(FIRE_SYSTEM, new FireSystem(this));
    }

    @Override
    public void destroy() {
        CollisionProxy.Instance().unregisterCollision(this);
    }
}
