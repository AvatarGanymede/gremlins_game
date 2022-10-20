package gremlins.levels.textlevels;

import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.Level;
import gremlins.monobehaviours.Collision;
import processing.core.PApplet;

import static gremlins.gameutils.GameConst.HEIGHT;
import static gremlins.gameutils.GameConst.WIDTH;

public class TextLevel extends Level {
    protected String m_Text;
    public TextLevel(){
        super();
        loadLevel();
    }
    @Override
    public void loadLevel() {
        GameProxy.Instance().loadLevel();
        GameProxy.Instance().gameRef.isLoaded = true;
    }

    @Override
    public void unloadLevel() {

    }

    @Override
    public void keyPressed(Integer key) {

    }

    @Override
    public void keyReleased(Integer key) {

    }

    @Override
    public void Update() {
        GameProxy.Instance().gameRef.textFont(GameProxy.Instance().titleFont);
        GameProxy.Instance().gameRef.fill(255);
        GameProxy.Instance().gameRef.text(m_Text, WIDTH/2, HEIGHT/2);
        PApplet.println(CollisionProxy.Instance().m_Go2MapIndex.size());
    }
}
