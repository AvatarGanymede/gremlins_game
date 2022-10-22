package gremlins.levels.textlevels;

import gremlins.gameutils.GameProxy;
import gremlins.levels.Level;

import static gremlins.gameutils.GameConst.HEIGHT;
import static gremlins.gameutils.GameConst.WIDTH;

public class TextLevel extends Level {
    public String text;
    public TextLevel(){
        super();
        loadLevel();
    }
    @Override
    public void loadLevel() {
        if(GameProxy.Instance().gameRef == null){
            return;
        }
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
        GameProxy.Instance().gameRef.text(text, WIDTH/2, HEIGHT/2);
    }
}
