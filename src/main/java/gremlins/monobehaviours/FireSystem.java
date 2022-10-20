package gremlins.monobehaviours;

import gremlins.gameobjects.*;
import gremlins.gameobjects.projectiles.FireBall;
import gremlins.gameobjects.projectiles.Slime;
import gremlins.gameutils.GameUtils;

import java.math.BigDecimal;

import static gremlins.gameutils.GameConst.*;

public class FireSystem extends MonoBehaviour{
    public BigDecimal shootTime;
    public double coolDownTime;
    public BigDecimal nextShootTime;
    public FireSystem(GameObject gameObject) {
        super(gameObject);
        shootTime = BigDecimal.valueOf(-1);
        nextShootTime = BigDecimal.valueOf(TIME_STAMP.doubleValue());
        switch (m_GameObject.type){
            case PLAYER -> coolDownTime = PLAYER_COOL_DOWN_TIME;
            case GREMLINS -> {
                coolDownTime = GREMLIN_COOL_DOWN_TIME;
                nextShootTime = nextShootTime.add(BigDecimal.valueOf(GameUtils.random.nextInt(5)));
            }
        }
    }

    @Override
    public void OnUpdate() {
        if(m_GameObject.type == GO_TYPE.GREMLINS && TIME_STAMP.compareTo(nextShootTime) >= 0){
            Gremlin gremlin = (Gremlin) m_GameObject;
            Slime.create((int)m_GameObject.position.x, (int)m_GameObject.position.y, gremlin.getDirection());
            nextShootTime = TIME_STAMP.add(BigDecimal.valueOf(coolDownTime));
        }
    }

    @Override
    public void keyPressed(Integer key) {
        if(key == FIRE_KEY && TIME_STAMP.compareTo(nextShootTime) >= 0){
            Player player = (Player) m_GameObject;
            FireBall.create((int)m_GameObject.position.x, (int)m_GameObject.position.y, player.getDirection());
            shootTime = TIME_STAMP;
            nextShootTime = TIME_STAMP.add(BigDecimal.valueOf(coolDownTime));
        }
    }

    @Override
    public void keyReleased(Integer key) {

    }
}
