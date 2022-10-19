package gremlins.gameutils;

import gremlins.Game;
import gremlins.gameobjects.GameObject;
import processing.core.PFont;

import static gremlins.gameutils.GameConst.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameProxy {
    private static GameProxy m_GameProxy;
    public Game gameRef;
    public HashMap<Integer, Boolean> registeredKey;
    public GameProxy(){
        Entities = new ConcurrentHashMap<>();
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
        Entities.clear();
        CollisionProxy.Instance().reset();
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
    public ConcurrentHashMap<Integer, GameObject> Entities;
    public PFont textFont;
    public PFont titleFont;
}
