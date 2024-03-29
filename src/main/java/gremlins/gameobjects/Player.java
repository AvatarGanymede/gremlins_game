package gremlins.gameobjects;

import static gremlins.gameutils.GameConst.*;

import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.textlevels.GameOverLevel;
import gremlins.levels.textlevels.LoseLevel;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.FireSystem;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

import java.math.BigInteger;

public class Player extends GameObject{
    public int lives;
    public BigInteger killedFrame;
    public Player(int x, int y){
        super(x, y);
        type = GO_TYPE.PLAYER;
        lives = MAX_PLAYER_LIVES;
        killedFrame = new BigInteger("-1");
        InitMonos();
    }
    public void beKilled(){
        if(killedFrame.intValue() != -1 && killedFrame.compareTo(FRAME_TICK) >= 0){
            return;
        }
        lives --;
        killedFrame = FRAME_TICK.add(BigInteger.valueOf(FPS));
        if(lives < 0){
            GameProxy.Instance().gameRef.level.unloadLevel();
            GameProxy.Instance().gameRef.level = new LoseLevel();
        }
    }
    public PVector getDirection(){
        Renderer renderer = (Renderer) m_Monos.get(RENDERER);
        return KEY2DIRECTION.get(PLAYER_INDEX2KEY.get(renderer.pathIndex)).copy();
    }
    @Override
    protected void InitMonos(){
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
        m_Monos.put(FIRE_SYSTEM, new FireSystem(this));
    }

    @Override
    public void destroy() {
        CollisionProxy.Instance().unregisterCollision(this);
    }

    public void keyPressed(Integer key){
        for (var monoKey : m_Monos.keySet()) {
            m_Monos.get(monoKey).keyPressed(key);
        }
    }
    public void keyReleased(Integer key){
        for (var monoKey : m_Monos.keySet()) {
            m_Monos.get(monoKey).keyReleased(key);
        }
    }
}
