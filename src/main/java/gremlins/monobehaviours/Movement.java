package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.CollisionProxy;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Movement extends MonoBehaviour {
    private final int m_Velocity;
    private PVector m_PrevVerticalMove;
    private PVector m_PrevHorizontalMove;
    public PVector prevPosition;
    public final PVector move;
    public Movement(GameObject gameObject){
        super(gameObject);
        move = new PVector(0, 0);
        this.m_GameObject = gameObject;
        switch (m_GameObject.type) {
            case PLAYER -> m_Velocity = PLAYER_VELOCITY;
            case FIREBALL -> m_Velocity = FIREBALL_VELOCITY;
            case GREMLINS -> m_Velocity = GREMLINS_VELOCITY;
            default -> m_Velocity = 0;
        }
    }

    @Override
    public void OnUpdate() {
        PVector move = PVector.mult(this.move, m_Velocity);
        prevPosition = m_GameObject.position.copy();
        if(this.move.equals(ZERO_VECTOR)){
            PVector position = m_GameObject.position;
            int tileSize = TILE_SIZE;
            if(position.x % tileSize != 0){
                PVector horizontalMove = PVector.mult(m_PrevHorizontalMove, m_Velocity);
                m_GameObject.position.add(horizontalMove);
            }
            if(position.y % tileSize != 0){
                PVector verticalMove = PVector.mult(m_PrevVerticalMove, m_Velocity);
                m_GameObject.position.add(verticalMove);
            }
        }else{
            m_GameObject.position.add(move);
        }
        CollisionProxy.Instance().checkMoveCollision(m_GameObject);
    }

    @Override
    public void keyPressed(Integer key) {
        if(!KEY2DIRECTION.containsKey(key)){
            return;
        }
        PVector move = KEY2DIRECTION.get(key);
        this.move.add(move);
    }

    @Override
    public void keyReleased(Integer key) {
        if(!KEY2DIRECTION.containsKey(key)){
            return;
        }
        m_PrevVerticalMove = move.copy(); m_PrevVerticalMove.x = 0;
        m_PrevHorizontalMove = move.copy(); m_PrevHorizontalMove.y = 0;
        PVector move = KEY2DIRECTION.get(key);
        this.move.sub(move);
    }
}
