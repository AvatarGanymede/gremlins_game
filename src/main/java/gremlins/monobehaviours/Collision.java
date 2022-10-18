package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.CollisionProxy;
import static gremlins.gameutils.GameConst.*;

import java.util.ArrayList;

public class Collision extends MonoBehaviour {
    private final boolean m_IsStatic;

    public Collision(GameObject gameObject, boolean isStatic) {
        super(gameObject);
        m_IsStatic = isStatic;
        CollisionProxy.Instance().registerCollision(gameObject);
    }

    public void onHit(GameObject collision){
        if(m_GameObject.type == GO_TYPE.PLAYER){
            if(collision.type == GO_TYPE.BRICKWALL || collision.type == GO_TYPE.STONEWALL){
                Movement movement = (Movement) m_GameObject.getMono(MOVEMENT);
                if(collision.position.x != m_GameObject.position.x){
                    m_GameObject.position.x = movement.prevPosition.x;
                }
                if(collision.position.y != m_GameObject.position.y){
                    m_GameObject.position.y = movement.prevPosition.y;
                }
            }
        }
    }

    @Override
    public void OnUpdate() {
        if(m_IsStatic){
            return;
        }
        ArrayList<GameObject> collisions = CollisionProxy.Instance().registerCollision(m_GameObject);
        for(GameObject collision : collisions){
            onHit(collision);
        }
    }

    @Override
    public void keyPressed(Integer key) {

    }

    @Override
    public void keyReleased(Integer key) {

    }
}
