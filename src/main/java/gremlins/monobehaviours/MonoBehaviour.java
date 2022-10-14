package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;

public abstract class MonoBehaviour {
    protected GameObject m_GameObject;
    MonoBehaviour(GameObject gameObject){
        m_GameObject = gameObject;
    }
    public abstract void OnUpdate();
    public abstract void keyPressed(Integer key);
    public abstract void keyReleased(Integer key);
}
