package gremlins.levels;

import gremlins.gameobjects.Player;
import gremlins.gameutils.GameProxy;

import java.math.BigDecimal;
import java.math.BigInteger;

import static gremlins.gameutils.GameConst.FRAME_TICK;
import static gremlins.gameutils.GameConst.TIME_STAMP;

public abstract class Level{
    public Player player;
    protected boolean m_IsUnloaded;
    public Level(){
        TIME_STAMP = BigDecimal.ZERO;
        FRAME_TICK = BigInteger.ZERO;
        GameProxy.Instance().gameRef.isLoaded = true;
    }
    public abstract void loadLevel();
    public abstract void unloadLevel();
    public abstract void keyPressed(Integer key);
    public abstract void keyReleased(Integer key);
    public abstract void Update();
}
