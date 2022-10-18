package gremlins.gameutils;

import gremlins.gameobjects.GameObject;

import java.util.ArrayList;

public class GameObjectList {
    private ArrayList<GameObject> m_GoList;
    public GameObjectList(){
        m_GoList = new ArrayList<>();
    }
    public void addGo(GameObject go){
        if(m_GoList.contains(go)){
            return;
        }
        m_GoList.add(go);
    }
    public void removeGo(GameObject go){
        if(!m_GoList.contains(go)){
            return;
        }
        m_GoList.remove(go);
    }
    public ArrayList<GameObject> getList(){
        return m_GoList;
    }

    public int count(){
        return m_GoList.size();
    }
}
