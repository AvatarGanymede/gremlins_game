package gremlins.gameobjects;

import gremlins.gameutils.GameConst;
import gremlins.monobehaviours.MonoBehaviour;
import processing.core.PVector;

import java.util.HashMap;

public abstract class GameObject {
    public GameConst.GO_TYPE type;
    public PVector position;
    protected HashMap<String, MonoBehaviour> m_Monos = new HashMap<>();
    public GameObject(int x, int y){
        position = new PVector(x, y);
    }
    protected abstract void InitMonos();
    public void Update(){
        for (var monoKey : m_Monos.keySet()) {
            m_Monos.get(monoKey).OnUpdate();
        }
    }
    public MonoBehaviour getMono(String monoName){
        if(!m_Monos.containsKey(monoName)){
            return null;
        }
        return m_Monos.get(monoName);
    }
    public abstract void destroy();
}