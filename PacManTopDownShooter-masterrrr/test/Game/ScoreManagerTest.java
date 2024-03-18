package Game;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreManagerTest {

    @Test
    public void testIncrementScore() {

        ScoreManager instance = ScoreManager.getInstance();
        instance.incrementScore();
        assertEquals(instance.getScore(), 10);
    }

    @Test
    public void testResetScore() {

        ScoreManager instance = ScoreManager.getInstance();
        instance.incrementScore();
        instance.resetScore();
        assertEquals(instance.getScore(), 0);
    }

    @Test
    public void testGetScore() {

        ScoreManager instance = ScoreManager.getInstance();
        int expResult = 20;
        instance.incrementScore();
        instance.incrementScore();
        int result = instance.getScore();
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckNewHighScore() {

        ScoreManager instance = ScoreManager.getInstance();
        instance.incrementScore();
        boolean expResult = true;
        boolean result = instance.checkNewHighScore();
        assertEquals(expResult, result);
    }
}
