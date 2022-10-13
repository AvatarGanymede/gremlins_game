package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;

public abstract class MonoBehaviours {
    protected GameObject m_GameObject;
    MonoBehaviours(GameObject gameObject){
        m_GameObject = gameObject;
    }
    public abstract void OnUpdate();
    public abstract void keyPressed(Integer key);
    public abstract void keyReleased(Integer key);
}
