package gremlins.gameobjects.projectiles;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Projectile extends GameObject {

    public Projectile(int x, int y) {
        super(x, y);
    }

    public void setMove(PVector move){
        Movement movement = (Movement) m_Monos.get(MOVEMENT);
        if(movement == null){
            return;
        }
        movement.move.set(move);
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
    }

    @Override
    public void destroy() {
        GameProxy.Instance().entities.remove(this.hashCode());
        CollisionProxy.Instance().unregisterCollision(this);
    }
}
