package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.GameConst;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;

import java.io.File;

public class Movement extends MonoBehaviours{
    private boolean m_IsMoving;
    private int m_Velocity;
    private GameConst.DIRECTION m_Direction;
    private GameObject m_GameObject;
    public Movement(GameObject gameObject){
        this.m_GameObject = gameObject;
        m_IsMoving = false;
        switch (m_GameObject.type) {
            case PLAYER -> m_Velocity = GameConst.PLAYER_VELOCITY;
            case FIREBALL -> m_Velocity = GameConst.FIREBALL_VELOCITY;
            case GREMLINS -> m_Velocity = GameConst.GREMLINS_VELOCITY;
            default -> m_Velocity = 0;
        }
    }
    public void StartMove(GameConst.DIRECTION dir){
        m_IsMoving = true;
        m_Direction = dir;
    }
    public void StopMove(){
        m_IsMoving = false;
    }

    @Override
    public void OnUpdate() {
        if(m_IsMoving){
            PVector move = PVector.mult(GameConst.MOVE[m_Direction.ordinal()], m_Velocity);
            PVector.add(m_GameObject.position, move, m_GameObject.position);
        }
    }
}
