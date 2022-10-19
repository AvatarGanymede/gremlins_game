package gremlins.gameutils;

import gremlins.Game;
import gremlins.gameobjects.FireBall;
import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Slime;
import org.checkerframework.checker.units.qual.C;

import static gremlins.gameutils.GameConst.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameProxy {
    private static GameProxy m_GameProxy;
    public Game gameRef;
    public HashMap<Integer, Boolean> registeredKey;
    public GameProxy(){
        registeredKey = new HashMap<>();
        registeredKey.put(LEFT_KEY, false);
        registeredKey.put(RIGHT_KEY, false);
        registeredKey.put(UP_KEY, false);
        registeredKey.put(DOWN_KEY, false);
        registeredKey.put(FIRE_KEY, false);

        Entities = new ConcurrentHashMap<>();
    }
    public static GameProxy Instance(){
        if(m_GameProxy == null){
            m_GameProxy = new GameProxy();
        }
        return m_GameProxy;
    }

    public ConcurrentHashMap<Integer, GameObject> Entities;
}
