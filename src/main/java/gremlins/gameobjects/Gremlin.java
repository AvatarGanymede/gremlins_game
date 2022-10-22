package gremlins.gameobjects;

import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.gameutils.GameUtils;
import gremlins.levels.GameLevel;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.FireSystem;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PApplet;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Gremlin extends GameObject{

    public Gremlin(int x, int y) {
        super(x, y);
        type = GO_TYPE.GREMLINS;
        InitMonos();
    }
    public void respawn(){
        Movement movement = (Movement) getMono(MOVEMENT);
        GameLevel level = (GameLevel) GameProxy.Instance().gameRef.level;
        position.set(GameUtils.randomRespawnPos(level.player.position));
    }

    public PVector getDirection(){
        Movement movement = (Movement) m_Monos.get(MOVEMENT);
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
