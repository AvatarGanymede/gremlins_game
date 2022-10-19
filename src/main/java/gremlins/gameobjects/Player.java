package gremlins.gameobjects;

import static gremlins.gameutils.GameConst.*;

import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.textlevels.GameOverLevel;
import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.FireSystem;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

public class Player extends GameObject{
    public int lives;
    public Player(int x, int y){
        super(x, y);
        type = GO_TYPE.PLAYER;
        lives = MAX_PLAYER_LIVES;
        InitMonos();
    }
    public void beKilled(){
        lives --;
        if(lives < 0){
            GameProxy.Instance().gameRef.level.unloadLevel();
            GameProxy.Instance().gameRef.level = new GameOverLevel();
        }
    }
    public PVector getDirection(){
        Renderer renderer = (Renderer) m_Monos.get(RENDERER);
        if(renderer == null){
            return null;
        }
        return KEY2DIRECTION.get(PLAYER_INDEX2KEY.get(renderer.pathIndex));
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
