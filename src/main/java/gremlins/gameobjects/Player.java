package gremlins.gameobjects;

import static gremlins.gameutils.GameConst.*;

import gremlins.monobehaviours.Collision;
import gremlins.monobehaviours.Movement;
import gremlins.monobehaviours.Renderer;

public class Player extends GameObject{
    public Player(int x, int y){
        super(x, y);
        type = GO_TYPE.PLAYER;
        InitMonos();
    }
    @Override
    protected void InitMonos(){
        m_Monos.put(MOVEMENT, new Movement(this));
        m_Monos.put(COLLISION, new Collision(this, false));
        m_Monos.put(RENDERER, new Renderer(this));
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
