/**
 * An object that represents a bullet fired from the plane.
 * @author Austin French and Dom Valles
 */
public class PlaneBullet {
  float targetX;
  float targetY;
  float x;
  float y;
  float deltaX;
  float deltaY;
  float slope;
  float angle;

  final int SPEED = 32;

  /**
   * A constructor for the PlaneBullet. Sets the bullet's velocity and
   * angle, among other things.
   *
   * @param x the bullet's initial x position
   * @param y the bullet's initial y position
   * @param targetX the x position of the bullet's target coordinates
   * @param targetY the y position of the bullet's target coordinates
   */
  public PlaneBullet(float x, float y, float targetX, float targetY) {
    this.x = x;
    this.y = y;
    this.targetX = targetX;
    this.targetY = targetY;

    deltaX = targetX - x;
    deltaY = targetY - y;
    
    if (deltaX == 0) {
      if (deltaY >= 0) {
        angle = (float) Math.PI * (1/2);
      } else {
        angle = (float) Math.PI * (3/2);
      }
    } else {
      slope = deltaY / deltaX;
      angle = atan(slope);
      if (deltaX < 0) {
        angle = (float) Math.PI + angle;
      }
    }
  }

  /**
   * Checks whether the bullet is outside the game window.
   *
   * @return whether it's out of bounds as a boolean
   */
  boolean outOfBounds() {
    if (x < 0 || y < 0 || x > WIDTH || y > HEIGHT) {
      return true;
    }
    return false;
  }

  /**
   * Returns the bullet's angle. Used by the plane to determine
   * what direction recoil should move it.
   *
   * @return the bullet's angle of travel
   */
  float getAngle() {
    return angle;
  }

  /**
   * Updates the bullet once per frame. Called by draw(), and
   * handles any actions that need to be done once per frame 
   * but are not related to the GUI.
   */
  void update() {
    x = x + SPEED * cos(angle);
    y = y + SPEED * sin(angle);
  }

  /**
   * Draws the bullet.
   */
  void draw() {
    translate(x, y);
    rotate(1.0/2.0 * (float) Math.PI + angle);
    image(bulletImg, - bulletDim/2.0, - bulletDim/2.0);
    rotate(- (1.0/2.0 * (float) Math.PI + angle));
    translate(-x, -y);
  }
  
  float getX() {
    return this.x;
  }
  
  float getY() {
    return this.y;
  }

}
