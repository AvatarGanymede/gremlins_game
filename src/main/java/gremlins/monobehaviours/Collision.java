package gremlins.monobehaviours;

import gremlins.gameobjects.GameObject;
import gremlins.gameobjects.Gremlin;
import gremlins.gameobjects.Player;
import gremlins.gameutils.CollisionProxy;
import gremlins.gameutils.GameProxy;
import gremlins.levels.GameLevel;

import static gremlins.gameutils.GameConst.*;

import java.util.ArrayList;

public class Collision extends MonoBehaviour {
    private final boolean m_IsStatic;

    public Collision(GameObject gameObject, boolean isStatic) {
        super(gameObject);
        m_IsStatic = isStatic;
        CollisionProxy.Instance().registerCollision(gameObject);
    }

    public void onHit(GameObject collision){
        if(GameProxy.Instance().gameRef.level.player == null){
            return;
        }
        Movement movement = (Movement) m_GameObject.getMono(MOVEMENT);
        if(m_GameObject.type == GO_TYPE.PLAYER){
            if(collision.type == GO_TYPE.BRICKWALL || collision.type == GO_TYPE.STONEWALL){
                if(movement.prevPosition.x % TILE_SIZE == 0 && collision.position.x != m_GameObject.position.x){
                    m_GameObject.position.x = movement.prevPosition.x;
                }
                if(CollisionProxy.isInCollision(m_GameObject, collision) && collision.position.y != m_GameObject.position.y){
                    m_GameObject.position.y = movement.prevPosition.y;
                }
            }
            if(collision.type == GO_TYPE.GREMLINS || collision.type == GO_TYPE.SLIME){
                Player player = (Player) m_GameObject;
                player.beKilled();
            }
            if(collision.type == GO_TYPE.DOOR){
                GameProxy.Instance().gameRef.level.unloadLevel();
                GameProxy.Instance().gameRef.level = new GameLevel();
            }
        }
        if(m_GameObject.type == GO_TYPE.GREMLINS){
            if(collision.type == GO_TYPE.BRICKWALL || collision.type == GO_TYPE.STONEWALL){
                m_GameObject.position.set(movement.prevPosition);
            }
            if(collision.type == GO_TYPE.PLAYER || collision.type == GO_TYPE.FIREBALL){
                Gremlin gremlin = (Gremlin) m_GameObject;
                gremlin.respawn();
            }
        }
        if(m_GameObject.type == GO_TYPE.FIREBALL){
            if(collision.type != GO_TYPE.PLAYER && collision.type != GO_TYPE.DOOR){
                m_GameObject.destroy();
            }
        }
        if(m_GameObject.type == GO_TYPE.SLIME){
            if(collision.type != GO_TYPE.GREMLINS && collision.type != GO_TYPE.DOOR && collision.type != GO_TYPE.SLIME){
                m_GameObject.destroy();
            }
        }
        if(m_GameObject.type == GO_TYPE.BRICKWALL){
            if(collision.type == GO_TYPE.FIREBALL){
                Renderer renderer = (Renderer) m_GameObject.getMono(RENDERER);
                renderer.beDestroyed = true;
            }
        }
    }

    @Override
    public void OnUpdate() {
        if(m_IsStatic){
            return;
        }
        ArrayList<GameObject> collisions = CollisionProxy.Instance().registerCollision(m_GameObject);
        for(GameObject collision : collisions){
            onHit(collision);
            Collision cCollision = (Collision) collision.getMono(COLLISION);
            cCollision.onHit(m_GameObject);
        }
        Movement movement = (Movement) m_GameObject.getMono(MOVEMENT);
        if(movement != null && m_GameObject.position.equals(movement.prevPosition)){
            if(m_GameObject.type == GO_TYPE.GREMLINS){
                movement.move.set(CollisionProxy.Instance().calcGremlinMove(m_GameObject, movement.move));
            }else{
                m_GameObject.destroy();
            }
        }
    }

    @Override
    public void keyPressed(Integer key) {

    }

    @Override
    public void keyReleased(Integer key) {

    }
}
