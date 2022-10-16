package gremlins.gameobjects;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.GameProxy;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;

import static gremlins.gameutils.GameConst.*;

public class FireBall extends GameObject {
    public FireBall(int x, int y) {
        super(x, y);
        type = GO_TYPE.FIREBALL;
        InitMonos();
    }

    public static void create(int x, int y){
        FireBall fireBall = new FireBall(x, y);
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
