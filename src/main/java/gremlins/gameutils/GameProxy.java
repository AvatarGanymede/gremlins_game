package gremlins.gameutils;

import gremlins.Game;
import gremlins.gameobjects.GameObject;
import org.checkerframework.checker.units.qual.A;
import processing.core.PFont;
import processing.core.PVector;

import static gremlins.gameutils.GameConst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameProxy {
    private static GameProxy m_GameProxy;
    public Game gameRef;
    public HashMap<Integer, Boolean> registeredKey;
    public GameProxy(){
        entities = new ConcurrentHashMap<>();
        nonWallTile = new ArrayList<>();
    }
    public static GameProxy Instance(){
        if(m_GameProxy == null){
            m_GameProxy = new GameProxy();
        }
        return m_GameProxy;
    }
    public void loadLevel(){
        registerKeys();
    }
    public void unloadLevel(){
        gameRef.isLoaded = false;
        unregisterKeys();
        entities.clear();
        nonWallTile.clear();
        CollisionProxy.Instance().reset();
    }
    public void registerNonWallTile(PVector pos){
        nonWallTile.add(pos);
    }
    private void registerKeys(){
        registeredKey = new HashMap<>();
        registeredKey.put(LEFT_KEY, false);
        registeredKey.put(RIGHT_KEY, false);
        registeredKey.put(UP_KEY, false);
        registeredKey.put(DOWN_KEY, false);
        registeredKey.put(FIRE_KEY, false);
    }
    private void unregisterKeys(){
        registeredKey.clear();
    }
    public ConcurrentHashMap<Integer, GameObject> entities;
    public ArrayList<PVector> nonWallTile;
    public PFont textFont;
    public PFont titleFont;
}
