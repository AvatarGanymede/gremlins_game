package gremlins.gameobjects;

import gremlins.gameutils.GameConst;
import gremlins.monobehaviours.MonoBehaviours;
import processing.core.PVector;

import java.util.HashMap;

public abstract class GameObject {
    public GameConst.GO_TYPE type;
    public PVector position;
    protected HashMap<String, MonoBehaviours> m_Monos = new HashMap<>();
    public GameObject(int x, int y){
        position = new PVector(x, y);
    }
    protected abstract void InitMonos();
    public void Update(){
        for (var monoKey : m_Monos.keySet()) {
            m_Monos.get(monoKey).OnUpdate();
        }
    }
}