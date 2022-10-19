package gremlins.levels.textlevels;

import gremlins.gameutils.GameProxy;
import gremlins.levels.GameLevel;

public class WelcomeLevel extends TextLevel {
    public WelcomeLevel(){
        super();
        m_Text = "PRESS ANY KEY\nTO START";
    }

    @Override
    public void unloadLevel() {
        GameProxy.Instance().unloadLevel();
        GameProxy.Instance().gameRef.level = new GameLevel();
    }

    @Override
    public void keyReleased(Integer key) {
        unloadLevel();
    }
}
