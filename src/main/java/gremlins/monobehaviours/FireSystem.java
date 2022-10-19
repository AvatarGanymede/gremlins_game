package gremlins.monobehaviours;

import gremlins.gameobjects.*;
import gremlins.gameutils.GameUtils;

import java.math.BigDecimal;

import static gremlins.gameutils.GameConst.*;

public class FireSystem extends MonoBehaviour{
    private double m_CoolDown;
    private BigDecimal m_NextShootTime;
    public FireSystem(GameObject gameObject) {
        super(gameObject);
        m_NextShootTime = BigDecimal.valueOf(TIME_STAMP.doubleValue());
        switch (m_GameObject.type){
            case PLAYER -> m_CoolDown = PLAYER_COOL_DOWN_TIME;
            case GREMLINS -> {
                m_CoolDown = GREMLIN_COOL_DOWN_TIME;
                m_NextShootTime = m_NextShootTime.add(BigDecimal.valueOf(GameUtils.random.nextInt(5)));
            }
        }
    }

    @Override
    public void OnUpdate() {
        if(m_GameObject.type == GO_TYPE.GREMLINS && TIME_STAMP.compareTo(m_NextShootTime) >= 0){
            Gremlin gremlin = (Gremlin) m_GameObject;
            Slime.create((int)m_GameObject.position.x, (int)m_GameObject.position.y, gremlin.getDirection());
            m_NextShootTime = TIME_STAMP.add(BigDecimal.valueOf(m_CoolDown));
        }
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
