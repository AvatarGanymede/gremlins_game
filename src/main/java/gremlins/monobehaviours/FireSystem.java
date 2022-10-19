package gremlins.monobehaviours;

import gremlins.gameobjects.FireBall;
import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Player;

import java.math.BigDecimal;

import static gremlins.gameutils.GameConst.*;

public class FireSystem extends MonoBehaviour{
    private double m_CoolDown;
    private BigDecimal m_NextShootTime;
    public FireSystem(GameObject gameObject) {
        super(gameObject);
        m_NextShootTime = BigDecimal.valueOf(0);
        switch (m_GameObject.type){
            case PLAYER -> m_CoolDown = PLAYER_COOL_DOWN_TIME;
            case GREMLINS -> m_CoolDown = GREMLIN_COOL_DOWN_TIME;
        }
    }

    @Override
    public void OnUpdate() {

    }

    @Override
    public void keyPressed(Integer key) {
        if(key == FIRE_KEY && TIME_STAMP.compareTo(m_NextShootTime) >= 0){
            Player player = (Player) m_GameObject;
            FireBall.create((int)m_GameObject.position.x, (int)m_GameObject.position.y, player.getDirection());
            m_NextShootTime = TIME_STAMP.add(BigDecimal.valueOf(m_CoolDown));
        }
    }

    @Override
    public void keyReleased(Integer key) {

    }
}
