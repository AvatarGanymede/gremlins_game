package gremlins.gameutils;

import processing.core.PVector;

import java.util.Random;

import static gremlins.gameutils.GameConst.*;

public class GameUtils {
    public static Random random = new Random();
    public static int strCount(String s, char c){
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == c){
                count ++;
            }
        }
        return count;
    }
    public static PVector randomRespawnPos(PVector playerPos) {
        PVector mapIndex = CollisionProxy.getMapIndex(playerPos);
        int index = random.nextInt(GameProxy.Instance().nonWallTile.size());
        PVector pos = GameProxy.Instance().nonWallTile.get(index);
        while(!CollisionProxy.Instance().checkCollision((int) pos.x, (int) pos.y) || mapIndex.dist(pos) <= RESPAWN_DISTANCE){
            index = random.nextInt(GameProxy.Instance().nonWallTile.size());
            pos = GameProxy.Instance().nonWallTile.get(index);
        }
        PVector respawn =  pos.copy();
        respawn.mult(TILE_SIZE);
        return respawn;
    }
}
