package gremlins.levels;

import gremlins.gameutils.GameProxy;

import static gremlins.gameutils.GameConst.*;

public class PrepareLevel extends Level {
    public String text;
    public PrepareLevel(){
        super();
        loadLevel();
    }

    @Override
    public void loadLevel() {
        text = "PRESS ANY KEY\nTO START";
        GameProxy.Instance().loadLevel();
        GameProxy.Instance().gameRef.isLoaded = true;
    }

    @Override
    public void unloadLevel() {
        GameProxy.Instance().unloadLevel();
        GameProxy.Instance().gameRef.level = new GameLevel();
    }

    @Override
    public void keyPressed(Integer key) {

    }

    @Override
    public void keyReleased(Integer key) {
        unloadLevel();
    }

    @Override
    public void Update() {
        GameProxy.Instance().gameRef.textFont(GameProxy.Instance().titleFont);
        GameProxy.Instance().gameRef.fill(255);
        GameProxy.Instance().gameRef.text(text, WIDTH/2, HEIGHT/2);
    }
}
