package gremlins.gameutils;

import gremlins.Game;

import static gremlins.gameutils.GameConst.*;

import java.util.HashMap;

public class GameProxy {
    private static GameProxy m_GameProxy;
    public Game gameRef;
    public HashMap<Integer, Boolean> registeredKey;
    public GameProxy(){
        this.registeredKey = new HashMap<>();
        registeredKey.put(LEFT_KEY, false);
        registeredKey.put(RIGHT_KEY, false);
        registeredKey.put(UP_KEY, false);
        registeredKey.put(DOWN_KEY, false);
    }
    public static GameProxy Instance(){
        if(m_GameProxy == null){
            m_GameProxy = new GameProxy();
        }
        return m_GameProxy;
    }
}
