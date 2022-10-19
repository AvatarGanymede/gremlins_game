package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

public class Movement extends MonoBehaviour {
    private final int m_Velocity;
    private PVector m_PrevVerticalMove;
    private PVector m_PrevHorizontalMove;
    public final PVector prevPosition;
    public final PVector move;
    public Movement(GameObject gameObject){
        super(gameObject);
        prevPosition = new PVector(gameObject.position.x, gameObject.position.y);
        move = new PVector(0, 0);
        this.m_GameObject = gameObject;
        switch (m_GameObject.type) {
            case PLAYER -> m_Velocity = PLAYER_VELOCITY;
            case FIREBALL -> m_Velocity = FIREBALL_VELOCITY;
            case GREMLINS -> m_Velocity = GREMLINS_VELOCITY;
            case SLIME -> m_Velocity = SLIME_VELOCITY;
            default -> m_Velocity = 0;
        }
    }
    private void playerMove(){
        PVector move = PVector.mult(this.move, m_Velocity);
        if(!this.move.equals(ZERO_VECTOR)){
            m_GameObject.position.add(move);
        }

        PVector position = m_GameObject.position;
        if(move.x == 0 && position.x % TILE_SIZE != 0 && m_PrevHorizontalMove != null){
            PVector horizontalMove = PVector.mult(m_PrevHorizontalMove, m_Velocity);
            m_GameObject.position.add(horizontalMove);
        }
        if(move.y == 0 && position.y % TILE_SIZE != 0 && m_PrevVerticalMove != null){
            PVector verticalMove = PVector.mult(m_PrevVerticalMove, m_Velocity);
            m_GameObject.position.add(verticalMove);
        }
    }

    private void gremlinMove(){
        PVector move = PVector.mult(this.move, m_Velocity);
        m_GameObject.position.add(move);
    }

    private void fireBallMove(){
        PVector move = PVector.mult(this.move, m_Velocity);
        m_GameObject.position.add(move);
    }

    private void slimeMove(){
        PVector move = PVector.mult(this.move, m_Velocity);
        m_GameObject.position.add(move);
    }

    private void checkBoarder(){
        if(m_GameObject.position.x < 0 || m_GameObject.position.x > TILE_SIZE*(TILE_NUM_X-1)){
            m_GameObject.position.x = prevPosition.x;
        }
        if(m_GameObject.position.y < 0 || m_GameObject.position.y > TILE_SIZE*(TILE_NUM_Y-1)){
            m_GameObject.position.y = prevPosition.y;
        }
    }

    @Override
    public void OnUpdate() {
        prevPosition.set(m_GameObject.position);
        switch (m_GameObject.type){
            case PLAYER -> playerMove();
            case GREMLINS -> gremlinMove();
            case FIREBALL -> fireBallMove();
            case SLIME -> slimeMove();
        }
        checkBoarder();
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
        if(move.y != 0){
            m_PrevVerticalMove = move.copy(); m_PrevVerticalMove.x = 0;
        }
        if(move.x != 0){
            m_PrevHorizontalMove = move.copy(); m_PrevHorizontalMove.y = 0;
        }
        PVector move = KEY2DIRECTION.get(key);
        this.move.sub(move);
    }
}
