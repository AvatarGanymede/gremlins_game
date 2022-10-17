package gremlins.gameobjects;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.GameProxy;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class FireBall extends GameObject {
    public FireBall(int x, int y) {
        super(x, y);
        type = GO_TYPE.FIREBALL;
        InitMonos();
    }

    public void setMove(PVector move){
        Movement movement = (Movement) m_Monos.get(MOVEMENT);
        if(movement == null){
            return;
        }
        movement.move.set(move);
    }

    public static void create(int x, int y, PVector move){
        FireBall fireBall = new FireBall(x, y);
        fireBall.setMove(move);
        GameProxy.Instance().FireBalls.put(fireBall.hashCode(), fireBall);
    }

    public void destroy(){
        GameProxy.Instance().FireBalls.remove(this.hashCode());
    }

    @Override
    protected void InitMonos() {
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
    }
}
