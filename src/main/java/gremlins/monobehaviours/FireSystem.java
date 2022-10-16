package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Player;
import gremlins.gameutils.GameProxy;

import static gremlins.gameutils.GameConst.*;

public class FireSystem extends MonoBehaviour{
    private Player m_Player;
    FireSystem(GameObject gameObject) {
        super(gameObject);
        m_Player = (Player) gameObject;
    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void keyPressed(Integer key) {
        if(key == FIRE_KEY){

        }
    }

    @Override
    public void keyReleased(Integer key) {

    }
}
