package gremlins.monobehaviours;

import gremlins.Game;
import gremlins.gameobjects.GameObject;
import gremlins.gameutils.GameProxy;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import static gremlins.gameutils.GameConst.*;

public class Renderer extends MonoBehaviour {
    public int pathIndex;
    private final ArrayList<PImage> m_Images;
    private final Stack<Integer> m_KeyStack;
    public Renderer(GameObject gameObject){
        super(gameObject);
        pathIndex = 0;
        m_KeyStack = new Stack<>();
        Game gameRef = GameProxy.Instance().gameRef;
        String[] paths;
        switch (m_GameObject.type) {
            case PLAYER -> paths = WIZARD_PATHS;
            case FIREBALL -> paths = FIREBALL_PATHS;
            case GREMLINS -> paths = GREMLIN_PATHS;
            case STONEWALL -> paths = STONE_WALL_PATHS;
            default -> paths = null;
        }
        m_Images = new ArrayList<>();
        if(paths != null){
            for(String path : paths){
                try{
                    m_Images.add(gameRef.loadImage(Objects.requireNonNull(gameRef.getClass().getResource(path)).getPath().replace("%20", " ")));
                }catch (NullPointerException ignored) { }
            }
        }
    }
    @Override
    public void OnUpdate() {
        if(m_Images == null){
            return;
        }
        PVector position = m_GameObject.position;
        GameProxy.Instance().gameRef.image(m_Images.get(pathIndex), position.x, position.y);
    }

    @Override
    public void keyPressed(Integer key) {
        if(!PLAYER_KEY2INDEX.containsKey(key)){
            return;
        }
        m_KeyStack.push(key);
        pathIndex = PLAYER_KEY2INDEX.get(key);
    }

    @Override
    public void keyReleased(Integer key) {
        if(!PLAYER_KEY2INDEX.containsKey(key)){
            return;
        }
        Stack<Integer> tmpStack = new Stack<>();
        while(!Objects.equals(key, m_KeyStack.peek())) {
            tmpStack.push(m_KeyStack.peek());
            m_KeyStack.pop();
        }
        m_KeyStack.pop();
        while(!tmpStack.empty()){
            m_KeyStack.push(tmpStack.peek());
            tmpStack.pop();
        }
        if(!m_KeyStack.empty()){
            pathIndex = PLAYER_KEY2INDEX.get(m_KeyStack.peek());
        }
    }
}
