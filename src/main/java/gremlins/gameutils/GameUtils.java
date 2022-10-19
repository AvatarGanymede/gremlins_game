package gremlins.gameutils;

import java.util.Random;

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
}
