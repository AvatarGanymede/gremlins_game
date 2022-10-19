package gremlins.levels;

import gremlins.gameobjects.*;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameObjectList;
import gremlins.gameutils.GameProxy;
import gremlins.gameutils.GameUtils;
import processing.core.PApplet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static gremlins.gameutils.GameConst.*;
import static gremlins.gameutils.GameConst.CONFIG;

public class GameLevel extends Level {
    public static int levelIndex = 0;
    public static int maxLevels;
    public Player player;
    private int m_wizardNum;
    private int m_doorNum;
    public GameLevel(){
        super();
        loadLevel();
    }

    @Override
    public void loadLevel() {
        GameProxy.Instance().loadLevel();
        loadLevelConfig();
        GameProxy.Instance().gameRef.isLoaded = true;
    }

    @Override
    public void unloadLevel() {
        GameProxy.Instance().unloadLevel();
        player = null;
        CollisionProxy.Instance().reset();
        levelIndex ++;
    }

    @Override
    public void keyPressed(Integer keyCode) {
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
        if(!GameProxy.Instance().registeredKey.containsKey(keyCode)){
            return;
        }
        GameProxy.Instance().registeredKey.put(keyCode, false);
        player.keyReleased(keyCode);
    }

    @Override
    public void Update() {
        for(Integer hashCode : GameProxy.Instance().Entities.keySet()){
            GameProxy.Instance().Entities.get(hashCode).Update();
        }
    }

    private void loadLevelConfig(){
        m_wizardNum = 0;
        m_doorNum = 0;
        maxLevels = CONFIG.getJSONArray("levels").size();
        if(levelIndex > maxLevels){
            return;
        }
        PLAYER_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getDouble("wizard_cooldown");
        GREMLIN_COOL_DOWN_TIME = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getDouble("enemy_cooldown");
        String levelPath = CONFIG.getJSONArray("levels").getJSONObject(levelIndex).getString("layout");
        if(!checkValidLevel(levelPath)){
            unloadLevel();
            loadLevel();
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
            case ' ' -> go = null;
            default -> {return false;}
        }
        if(m_wizardNum > 1 || m_doorNum > 1){
            return false;
        }
        if(go != null){
            GameProxy.Instance().Entities.put(go.hashCode(), go);
        }
        return true;
    }
}
