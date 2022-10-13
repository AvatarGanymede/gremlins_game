package gremlins.gameobjects;

import gremlins.gameutils.GameConst;
import gremlins.monobehaviours.MonoBehaviours;
import processing.core.PVector;

import java.util.ArrayList;

public abstract class GameObject {
    public GameConst.GO_TYPE type;
    public PVector position;
    protected ArrayList<MonoBehaviours> m_Monos = new ArrayList<MonoBehaviours>();
    public GameObject(){
        position = new PVector(0, 0);
    }
    public GameObject(int x, int y){
        position = new PVector(x, y);
    }
    protected abstract void InitMonos();
}