package gremlins.levels;

import gremlins.Game;
import gremlins.gameobjects.*;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.gameutils.GameUtils;
import gremlins.levels.textlevels.GameOverLevel;
import gremlins.levels.textlevels.WinLevel;
import gremlins.monobehaviours.FireSystem;
import gremlins.monobehaviours.Renderer;
import processing.core.PVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import static gremlins.gameutils.GameConst.*;
import static gremlins.gameutils.GameConst.CONFIG;

public class GameLevel extends Level {
    public static int levelIndex = 0;
    public static int maxLevels;
    public static boolean loadSuccess = false;
    public static boolean win = false;
    private int m_wizardNum;
    private int m_doorNum;
    public GameLevel(){
        super();
        loadLevel();
    }

    @Override
    public void loadLevel() {
        if(GameProxy.Instance().gameRef == null){
            return;
        }
        GameProxy.Instance().loadLevel();
        loadLevelConfig();
        GameProxy.Instance().gameRef.isLoaded = true;
        m_IsUnloaded = false;
    }

    @Override
    public void unloadLevel() {
        GameProxy.Instance().unloadLevel();
        player = null;
        CollisionProxy.Instance().reset();
        levelIndex ++;
        m_IsUnloaded = true;
    }

    @Override
    public void keyPressed(Integer keyCode) {
        if(m_IsUnloaded){
            return;
        }
        if(!GameProxy.Instance().registeredKey.containsKey(keyCode)){
            return;
        }
        if(GameProxy.Instance().registeredKey.get(keyCode)){
            return;
        }
        GameProxy.Instance().registeredKey.put(keyCode, true);
        player.keyPressed(keyCode);
    }

    @Override
    public void keyReleased(Integer keyCode) {
        if(m_IsUnloaded){
            return;
        }
        if(!GameProxy.Instance().registeredKey.containsKey(keyCode)){
            return;
        }
        if(!GameProxy.Instance().registeredKey.get(keyCode)){
            return;
        }
        GameProxy.Instance().registeredKey.put(keyCode, false);
        player.keyReleased(keyCode);
    }

    @Override
    public void Update() {
        if(!loadSuccess){
            GameProxy.Instance().gameRef.level.unloadLevel();
            GameProxy.Instance().gameRef.level = new GameOverLevel();
            return;
        }
        if(win){
            GameProxy.Instance().gameRef.level.unloadLevel();
            GameProxy.Instance().gameRef.level = new WinLevel();
            return;
        }
        for(Integer hashCode : GameProxy.Instance().entities.keySet()){
            if(m_IsUnloaded){
                return;
            }
            GameObject entity = GameProxy.Instance().entities.get(hashCode);
            if(entity != null){
                GameProxy.Instance().entities.get(hashCode).Update();
            }
        }
        UIUpdate();
    }

    public void UIUpdate(){
        float top = (float) HEIGHT - (float) BOTTOM_BAR;
        float bottom = (float) HEIGHT;
        float offsetX = 10;
        float offsetY = (top+bottom)/2;
        GameProxy.Instance().gameRef.textFont(GameProxy.Instance().textFont);
        GameProxy.Instance().gameRef.fill(FONT_COLOR);

        // Lives:
        String lives = "Lives: ";
        offsetX += GameProxy.Instance().gameRef.textWidth(lives)/2;
        GameProxy.Instance().gameRef.text(lives,+ offsetX, offsetY+4);
        offsetX += GameProxy.Instance().gameRef.textWidth(lives)/2;

        Renderer renderer = (Renderer) player.getMono(RENDERER);
        for(int i = 0; i < player.lives; i++){
            GameProxy.Instance().gameRef.image(renderer.images.get(0), offsetX + i*TILE_SIZE, offsetY-TILE_SIZE/2);
        }

        offsetX += TILE_SIZE*MAX_PLAYER_LIVES + 60;

        // level
        String level = String.format("Level %d/%d", levelIndex+1, maxLevels);
        GameProxy.Instance().gameRef.text(level, offsetX, offsetY+4);
        offsetX += GameProxy.Instance().gameRef.textWidth(level)/2;

        offsetX = WIDTH - 10 - COOL_DOWN_BAR_WIDTH;
        // cool down bar
        FireSystem fireSys = (FireSystem) player.getMono(FIRE_SYSTEM);
        if(fireSys.shootTime.compareTo(BigDecimal.valueOf(-1)) != 0 && TIME_STAMP.compareTo(fireSys.nextShootTime) < 0){
            GameProxy.Instance().gameRef.fill(0);
            GameProxy.Instance().gameRef.rect(offsetX, offsetY-COOL_DOWN_BAR_HEIGHT/2, COOL_DOWN_BAR_WIDTH, COOL_DOWN_BAR_HEIGHT);
            GameProxy.Instance().gameRef.fill(255);
            GameProxy.Instance().gameRef.rect(offsetX+COOL_DOWN_BAR_BOLD, offsetY-(COOL_DOWN_BAR_HEIGHT/2-COOL_DOWN_BAR_BOLD), COOL_DOWN_BAR_WIDTH-COOL_DOWN_BAR_BOLD*2, COOL_DOWN_BAR_HEIGHT-COOL_DOWN_BAR_BOLD*2);
            BigDecimal deltaTime = TIME_STAMP.subtract(fireSys.shootTime);
            double ratio = deltaTime.doubleValue()/fireSys.coolDownTime;
            double barWidth = COOL_DOWN_BAR_WIDTH * ratio;
            GameProxy.Instance().gameRef.fill(0);
            GameProxy.Instance().gameRef.rect(offsetX, offsetY-COOL_DOWN_BAR_HEIGHT/2, (float) barWidth, COOL_DOWN_BAR_HEIGHT);
        }
    }

    private void loadLevelConfig(){
        m_wizardNum = 0;
        m_doorNum = 0;
        maxLevels = CONFIG.getJSONArray("levels").size();
        if(levelIndex >= maxLevels){
            win = loadSuccess;
            return;
        }
        PLAYER_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getDouble("wizard_cooldown");
        GREMLIN_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getDouble("enemy_cooldown");
        String levelPath = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getString("layout");
        if(!checkValidLevel(levelPath)){
            unloadLevel();
            loadLevel();
        }else{
            loadSuccess = true;
        }
    }
    private boolean checkValidLevel(String levelPath){
        boolean isValid = true;
        int lineCount = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(levelPath));
            String line = reader.readLine();
            while(line != null){
                if(line.length() != TILE_NUM_X){
                    isValid = false;
                    break;
                }
                if(lineCount == 0 || lineCount == TILE_NUM_Y-1){
                    if(GameUtils.strCount(line, 'X') != TILE_NUM_X){
                        isValid = false;
                        break;
                    }
                }else{
                    if(line.charAt(0) != 'X' || line.charAt(line.length()-1) != 'X'){
                        isValid = false;
                        break;
                    }
                }
                for(int i = 0; i < line.length(); i++){
                    char e = line.charAt(i);
                    if(!loadEntity(e, i, lineCount)){
                        isValid = false;
                        break;
                    }
                }
                lineCount++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            isValid = false;
        }
        if(lineCount != TILE_NUM_Y){
            return false;
        }
        if(m_wizardNum != 1 || m_doorNum != 1){
            return false;
        }
        return isValid;
    }

    private boolean loadEntity(char e, int x, int y){
        int cordX = x * TILE_SIZE;
        int cordY = y * TILE_SIZE;
        GameObject go;
        switch (e){
            case 'X' -> go = new StoneWall(cordX, cordY);
            case 'B' -> go = new BrickWall(cordX, cordY);
            case 'W' -> {
                go = new Player(cordX, cordY);
                player = (Player) go;
                m_wizardNum ++;
            }
            case 'G' -> go = new Gremlin(cordX, cordY);
            case 'E' -> {
                go = new Door(cordX, cordY);
                m_doorNum ++;
            }
            case ' ' -> {
                go = null;
                GameProxy.Instance().registerNonWallTile(new PVector(x, y));
            }
            default -> {return false;}
        }
        if(m_wizardNum > 1 || m_doorNum > 1){
            return false;
        }
        if(go != null){
            GameProxy.Instance().entities.put(go.hashCode(), go);
        }
        return true;
    }
}
