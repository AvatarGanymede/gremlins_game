package gremlins.gameutils;

import gremlins.gameobjects.GameObject;
import gremlins.monobehaviours.Movement;
import processing.core.PVector;

import java.util.HashMap;

import static gremlins.gameutils.GameConst.*;

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
        return new PVector(Math.round(pos.x/TILE_SIZE), Math.round(pos.y/TILE_SIZE));
    }

    public void registerCollision(GameObject go){
        int hashCode = go.hashCode();
        PVector mapIndex = getMapIndex(go.position);
        if(m_Go2MapIndex.containsKey(hashCode)){
            if(!m_Go2MapIndex.get(hashCode).equals(mapIndex)){
                m_Go2MapIndex.get(hashCode).set(mapIndex);
            }
        }else{
            m_CollisionMap[(int) mapIndex.x][(int) mapIndex.y] = go;
            m_Go2MapIndex.put(hashCode, mapIndex);
        }
    }

    public void unregisterCollision(GameObject go){
        int hashCode = go.hashCode();
        if(m_Go2MapIndex.containsKey(hashCode)){
            PVector mapIndex = m_Go2MapIndex.get(hashCode);
            m_CollisionMap[(int)mapIndex.x][(int)mapIndex.y] = null;
            m_Go2MapIndex.remove(hashCode);
        }
    }

    public void checkMoveCollision(GameObject go){
        Movement movement = (Movement) go.getMono(MOVEMENT);
        if(movement == null){
            return;
        }
        PVector mapIndex = getMapIndex(go.position);
        int curMapIndexY = (int)mapIndex.y + (int)movement.move.y;
        int curMapIndexX = (int)mapIndex.x + (int)movement.move.x;
        if(movement.move.y != 0 && movement.prevPosition.y % TILE_SIZE == 0){
            // check vertical movement
            if(curMapIndexY < 0 || curMapIndexY > TILE_NUM_Y-1 || isWall((int)mapIndex.x, curMapIndexY)){
                go.position.y = movement.prevPosition.y;
            }
        }
        if(movement.move.x != 0 && movement.prevPosition.x % TILE_SIZE == 0){
            // check horizontal movement
            if(curMapIndexX < 0 || curMapIndexX > TILE_NUM_X-1 || isWall(curMapIndexX, (int)mapIndex.y)){
                go.position.x = movement.prevPosition.x;
            }
        }
    }

    private boolean isWall(int x, int y){
        GameObject go = m_CollisionMap[x][y];
        return go != null && (go.type == GO_TYPE.BRICKWALL || go.type == GO_TYPE.STONEWALL);
    }

    private final GameObject[][] m_CollisionMap = new GameObject[TILE_NUM_X][TILE_NUM_Y];
    private final HashMap<Integer, PVector> m_Go2MapIndex = new HashMap<>();
}
