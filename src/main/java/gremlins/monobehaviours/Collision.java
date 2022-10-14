package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameutils.CollisionProxy;

public class Collision extends MonoBehaviour {
    private final boolean m_IsStatic;

    public Collision(GameObject gameObject, boolean isStatic) {
        super(gameObject);
        m_IsStatic = isStatic;
        CollisionProxy.Instance().registerCollision(gameObject);
    }

    @Override
    public void OnUpdate() {
        if(m_IsStatic){
            return;
        }
        CollisionProxy.Instance().registerCollision(m_GameObject);
    }

    @Override
    public void keyPressed(Integer key) {

    }

    @Override
    public void keyReleased(Integer key) {

    }
}
