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

    public PVector calcGremlinMove(GameObject go, PVector prevMove){
        Movement movement = (Movement) go.getMono(MOVEMENT);
        if(movement == null){
            return null;
        }
        PVector mapIndex = getMapIndex(go.position);
        float prevMoveMapIndexX = mapIndex.x + prevMove.x;
        float prevMoveMapIndexY = mapIndex.y + prevMove.y;
        if (!prevMove.equals(ZERO_VECTOR) && !isWall((int) prevMoveMapIndexX, (int) prevMoveMapIndexY)) {
            return prevMove;
        }
        ArrayList<PVector> choices = new ArrayList<>();
        for(Integer dir : KEY2DIRECTION.keySet()){
            PVector dirMove = KEY2DIRECTION.get(dir);
            if((dirMove.x*prevMove.x + dirMove.y*prevMove.y == 0) && !isWall((int)(dirMove.x+mapIndex.x), (int)(dirMove.y+mapIndex.y))){
                choices.add(dirMove);
            }
        }
        if(choices.isEmpty()){
            return new PVector(0-prevMove.x, 0-prevMove.y);
        }else{
            return choices.get(random.nextInt(choices.size()));
        }
    }

    public void checkMoveCollision(GameObject go){
        Movement movement = (Movement) go.getMono(MOVEMENT);
        if(movement == null){
            return;
        }
        float moveX = (go.position.x - movement.prevPosition.x) / PLAYER_VELOCITY;
        float moveY = (go.position.y - movement.prevPosition.y) / PLAYER_VELOCITY;
        PVector mapIndex = getMapIndex(movement.prevPosition);
        if(movement.prevPosition.x % TILE_SIZE == 0){
            // check horizontal movement
            if(moveX > 0){
                if(movement.prevPosition.y % TILE_SIZE == 0 && isWall((int)mapIndex.x+1, (int)mapIndex.y)){
                    go.position.x = movement.prevPosition.x;
                }
                else if(movement.prevPosition.y % TILE_SIZE != 0 && (isWall((int)mapIndex.x+1, (int)mapIndex.y) || isWall((int)mapIndex.x+1, (int)mapIndex.y+1))){
                    go.position.x = movement.prevPosition.x;
                }
            }
            if(moveX < 0){
                if(movement.prevPosition.y % TILE_SIZE == 0 && isWall((int)mapIndex.x-1, (int)mapIndex.y)){
                    go.position.x = movement.prevPosition.x;
                }
                else if(movement.prevPosition.y % TILE_SIZE != 0 && (isWall((int)mapIndex.x-1, (int)mapIndex.y) || isWall((int)mapIndex.x-1, (int)mapIndex.y+1))){
                    go.position.x = movement.prevPosition.x;
                }else{
                    mapIndex.x -= 1;
                }
            }
        }
        mapIndex.x = (float)Math.floor(go.position.x/TILE_SIZE);
        movement.prevPosition.x = go.position.x;
        if(movement.prevPosition.y % TILE_SIZE == 0){
            // check vertical movement
            if(moveY > 0){
                if(movement.prevPosition.x % TILE_SIZE == 0 && isWall((int)mapIndex.x, (int)mapIndex.y+1)){
                    go.position.y = movement.prevPosition.y;
                }
                else if(movement.prevPosition.x % TILE_SIZE != 0 && (isWall((int)mapIndex.x, (int)mapIndex.y+1) || isWall((int)mapIndex.x+1, (int)mapIndex.y+1))){
                    go.position.y = movement.prevPosition.y;
                }
            }
            if(moveY < 0){
                if(movement.prevPosition.x % TILE_SIZE == 0 && isWall((int)mapIndex.x, (int)mapIndex.y-1)){
                    go.position.y = movement.prevPosition.y;
                }
                else if(movement.prevPosition.x % TILE_SIZE != 0 && (isWall((int)mapIndex.x, (int)mapIndex.y-1) || isWall((int)mapIndex.x+1, (int)mapIndex.y-1))){
                    go.position.y = movement.prevPosition.y;
                }
            }
        }
    }

    public ArrayList<GameObject> checkCollision(GameObject go){
        ArrayList<GameObject> collisions = new ArrayList<>();
        PVector mapIndex = getMapIndex(go.position);
        for(int i = (int)mapIndex.x-1; i <= (int)mapIndex.x+1; i++){
            for(int j = (int)mapIndex.y-1; j <= (int)mapIndex.y+1; j++){
                if(i < 0 || i > TILE_NUM_X-1){
                    continue;
                }
                if(j < 0 || j > TILE_NUM_Y-1){
                    continue;
                }
                GameObject collision = m_CollisionMap[i][j];
                if(collision!= null){
                    collisions.add(collision);
                }
            }
        }
        return collisions;
    }

    private boolean isWall(int x, int y){
        if(x < 0 || x > TILE_NUM_X-1){
            return true;
        }
        if(y < 0 || y > TILE_NUM_Y-1){
            return true;
        }
        GameObject go = m_CollisionMap[x][y];
        return go != null && (go.type == GO_TYPE.BRICKWALL || go.type == GO_TYPE.STONEWALL);
    }

    private final GameObject[][] m_CollisionMap = new GameObject[TILE_NUM_X][TILE_NUM_Y];
    private final HashMap<Integer, PVector> m_Go2MapIndex = new HashMap<>();
}
