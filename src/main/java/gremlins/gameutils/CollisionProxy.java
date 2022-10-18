package gremlins.gameutils;

import gremlins.gameobjects.GameObject;
import gremlins.monobehaviours.Movement;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

import static gremlins.gameutils.GameConst.*;
import static gremlins.gameutils.GameUtils.*;

public class CollisionProxy {
    private static CollisionProxy m_Instance;
    public static CollisionProxy Instance(){
        if(m_Instance == null){
            m_Instance = new CollisionProxy();
        }
        return m_Instance;
    }
    public CollisionProxy() { }

    public static PVector getMapIndex(PVector pos){
        return new PVector((float)Math.floor(pos.x/TILE_SIZE), (float)Math.floor(pos.y/TILE_SIZE));
    }

    public ArrayList<GameObject> registerCollision(GameObject go){
        int hashCode = go.hashCode();
        PVector mapIndex = getMapIndex(go.position);
        if(m_Go2MapIndex.containsKey(hashCode)){
            if(!m_Go2MapIndex.get(hashCode).equals(mapIndex)){
                PVector prevMapIndex = m_Go2MapIndex.get(hashCode);
                m_CollisionMap[(int) prevMapIndex.x][(int) prevMapIndex.y].removeGo(go);
            }
        }else{
            m_Go2MapIndex.put(hashCode, new PVector(0, 0));
        }
        if(m_CollisionMap[(int) mapIndex.x][(int) mapIndex.y] == null){
            m_CollisionMap[(int) mapIndex.x][(int) mapIndex.y] = new GameObjectList();
        }

        m_CollisionMap[(int) mapIndex.x][(int) mapIndex.y].addGo(go);
        m_Go2MapIndex.get(hashCode).set(mapIndex);
        return checkCollision(go);
    }

    public void unregisterCollision(GameObject go){
        int hashCode = go.hashCode();
        if(m_Go2MapIndex.containsKey(hashCode)){
            PVector mapIndex = m_Go2MapIndex.get(hashCode);
            m_CollisionMap[(int)mapIndex.x][(int)mapIndex.y].removeGo(go);
            m_Go2MapIndex.remove(hashCode);
        }
    }

    public ArrayList<GameObject> checkCollision(GameObject go){
        ArrayList<GameObject> collisions = new ArrayList<>();
        PVector mapIndex = getMapIndex(go.position);
        for(int i = (int)mapIndex.x-1; i <= (int)mapIndex.x+1; i++){
            if(i < 0 || i > TILE_NUM_X-1){
                continue;
            }
            for(int j = (int)mapIndex.y-1; j <= (int)mapIndex.y+1; j++){
                if(j < 0 || j > TILE_NUM_Y-1){
                    continue;
                }
                if(m_CollisionMap[i][j] == null || m_CollisionMap[i][j].count() == 0){
                    continue;
                }
                for(GameObject collision : m_CollisionMap[i][j].getList()){
                    if(collision.equals(go)){
                        continue;
                    }
                    if(isInCollision(go, collision)){
                        collisions.add(collision);
                    }
                }
            }
        }
        return collisions;
    }

    public static boolean isInCollision(GameObject a, GameObject b){
        PVector posA = a.position;
        PVector posB = b.position;
        return  posA.x < posB.x + TILE_SIZE &&
                posA.x + TILE_SIZE > posB.x &&
                posA.y < posB.y + TILE_SIZE &&
                posA.y + TILE_SIZE > posB.y;
    }

    private final GameObjectList[][] m_CollisionMap = new GameObjectList[TILE_NUM_X][TILE_NUM_Y];
    private final HashMap<Integer, PVector> m_Go2MapIndex = new HashMap<>();
}
