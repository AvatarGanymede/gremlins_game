package gremlins.gameutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    @Test
    void strCount() {
        assertEquals(0, GameUtils.strCount("", 'c'));
        assertEquals(0, GameUtils.strCount("XXXXXXXXXXXXXXXXXXXXXXXXX", 'C'));
        assertEquals(1, GameUtils.strCount("A", 'A'));
    }

    @Test
    void randomRespawnPos() {

    }
}