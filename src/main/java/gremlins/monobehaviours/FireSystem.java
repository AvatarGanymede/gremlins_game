package gremlins.monobehaviours;

import gremlins.gameobjects.FireBall;
import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Player;
import gremlins.gameutils.GameProxy;

import static gremlins.gameutils.GameConst.*;

public class FireSystem extends MonoBehaviour{
    private Player m_Player;
    public FireSystem(GameObject gameObject) {
        super(gameObject);
        m_Player = (Player) gameObject;
    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void keyPressed(Integer key) {
        if(key == FIRE_KEY){
            Player player = (Player) m_GameObject;
            FireBall.create((int)m_GameObject.position.x, (int)m_GameObject.position.y, player.getDirection());
        }
    }

    @Override
    public void keyReleased(Integer key) {

    }
}
