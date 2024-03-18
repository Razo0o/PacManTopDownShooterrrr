package Game;

import static Game.Game.Score;
import org.newdawn.slick.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Iterator;

/**
 * Interface for representing an enemy in the game.
 */
interface Enemy {
    // Updates the enemy's position based on player coordinates and delta time
    void update(Point playerCoordinates, int delta);

    // Renders the enemy
    void render();

    // Detects collision with bullets in an ArrayList and removes bullets accordingly
    void detectCollisionWithBullet(ArrayList<Bullet> bullets);

    // Checks if the enemy is colliding with bullets in an ArrayList
    boolean isCollidingWithBullets(ArrayList<Bullet> bullets);

    // Checks if the enemy is colliding with bullets in a LinkedList
    boolean isCollidingWithBullets(LinkedList<Bullet> bullets);

    // Loads the enemy's image
    void loadImage();

    // Loads the enemy's image based on an index
    void loadImage(int imageIndex);

    // Checks if the enemy is alive
    boolean isAlive();

    // Gets the X coordinate of the enemy
    int getX();

    // Gets the Y coordinate of the enemy
    int getY();

    // Gets the radius of the enemy
    int getRadius();

    // Kills the enemy and increments the score
    void kill();

    // Removes the enemy
    void remove();

    // Sets the speed of the enemy
    void setSpeed(int newSpeed);

    // Gets the ID of the player to follow
    int getPlayerToFollow();
}

/**
 * Public class representing a factory to create instances of Enemy.
 */
public class EnemyFactory {
    // Creates a default enemy with specified start coordinates
    public static Enemy createEnemy(int startX, int startY) {
        return new GhostEnemy(startX, startY);
    }

    // Creates an enemy with specified start coordinates, image index, and player to follow
    public static Enemy createEnemy(int startX, int startY, int imageIndex, int playerToFollow) {
        return new GhostEnemy(startX, startY, imageIndex, playerToFollow);
    }
}

/**
 * Class representing an enemy in the game.
 */
class GhostEnemy implements Enemy {
    private Image ghostImage;
    private Point coordinates = new Point(0, 0);
    private boolean alive = false;
    private float speed = 168;
    private float dx;
    private float dy;
    private int diameter = 84;
    private int radius = diameter / 2;
    private int playerToFollow;

    public GhostEnemy(int startX, int startY) {
        this.coordinates.setLocation(startX, startY);
        loadImage();
    }

    public GhostEnemy(int startX, int startY, int imageIndex, int playerToFollow) {
        this.coordinates.setLocation(startX, startY);
        this.playerToFollow = playerToFollow;
        loadImage(imageIndex);
    }

    @Override
    public void update(Point playerCoordinates, int delta) {
        float rad = (float) (Math.atan2(playerCoordinates.x - this.coordinates.getX(), this.coordinates.getY() - playerCoordinates.y));
        this.dx = (float) Math.sin(rad) * this.speed;
        this.dy = -(float) Math.cos(rad) * this.speed;

        float x = (float) this.coordinates.getX();
        float y = (float) this.coordinates.getY();
        x += this.dx * delta / 1000;
        y += this.dy * delta / 1000;

        this.alive = true;
        this.coordinates.setLocation(x, y);
    }

    @Override
    public void render() {
        if (this.isAlive())
            this.ghostImage.drawCentered((float) this.coordinates.getX(), (float) this.coordinates.getY());
    }

    @Override
    public void detectCollisionWithBullet(ArrayList<Bullet> bullets) {
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (this.isAlive() && bullet.isFired())
                if (Math.sqrt((bullet.getX() - this.getX()) * (bullet.getX() - this.getX()) +
                        (bullet.getY() - this.getY()) * (bullet.getY() - this.getY())) <=
                        this.radius + bullet.getRadius()) {
                    this.alive = false;
                    Score.incrementScore();
                    iter.remove();
                }
        }
    }

    @Override
    public boolean isCollidingWithBullets(ArrayList<Bullet> bullets) {
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (this.isAlive() && bullet.isFired())
                if (Math.sqrt((bullet.getX() - this.getX()) * (bullet.getX() - this.getX()) +
                        (bullet.getY() - this.getY()) * (bullet.getY() - this.getY())) <=
                        this.radius + bullet.getRadius()) {
                    Score.incrementScore();
                    iter.remove();
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean isCollidingWithBullets(LinkedList<Bullet> bullets) {
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (this.isAlive() && bullet.isFired())
                if (Math.sqrt((bullet.getX() - this.getX()) * (bullet.getX() - this.getX()) +
                        (bullet.getY() - this.getY()) * (bullet.getY() - this.getY())) <=
                        this.radius + bullet.getRadius()) {
                    Score.incrementScore();
                    iter.remove();
                    return true;
                }
        }
        return false;
    }

    @Override
    public void loadImage() {
        Random r = new Random();
        this.ghostImage = Game.ghosts[r.nextInt(4)];
    }

    @Override
    public void loadImage(int imageIndex) {
        this.ghostImage = Game.ghosts[imageIndex];
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public int getX() {
        return (int) this.coordinates.getX();
    }

    @Override
    public int getY() {
        return (int) this.coordinates.getY();
    }

    @Override
    public int getRadius() {
        return this.radius;
    }

    @Override
    public void kill() {
        this.alive = false;
        Score.incrementScore();
    }

    @Override
    public void remove() {
        this.alive = false;
    }

    @Override
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    @Override
    public int getPlayerToFollow() {
        return this.playerToFollow;
    }
}
