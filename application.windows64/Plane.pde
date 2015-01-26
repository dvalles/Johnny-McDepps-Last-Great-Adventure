import java.lang.Math;
import java.util.ArrayList;

/**
 * The Plane class represents the players' aircraft, controlled
 * by player 1 using the arrow keys and spacebar.
 * @author Austin French and Dom Valles
 */
public class Plane {
  float x;
  float y;
  float speed;
  float degrees;
  float angle;
  float gunX;
  float gunY;
  int count;
  boolean smoke;
  int smokeTimer;
  ArrayList<Smoke> smokeList;
  ArrayList<PlaneBullet> bulletList;
  int lives;

  final float MIN_SPEED = 1.0;
  final float MAX_SPEED = 10;
  final float SPEED_INCREMENT = 0.09;
  final float TURN_INCREMENT = 4;
  final int SMOKE_RATE = 15;
  final int MAX_LIVES = 3;
  final int RECOIL = 15;

  /**
   * A constructor for the Plane.
   *
   * @param startingX the x value of the plane's starting position
   * @param startingY the y value of the plane's starting position
   */
  public Plane (float startingX, float startingY) {
    x = startingX;
    y = startingY;
    gunX = x + 10;
    gunY = y;
    degrees = 180;
    speed = 1;
    count = 0;
    smoke = false;
    smokeList = new ArrayList<Smoke>();
    smokeTimer = 0;
    bulletList = new ArrayList<PlaneBullet>();
    lives = MAX_LIVES;
  }

  /**
   * Spawns a bullet at the plane's current coordinates, and
   * gives the bullet target coordinates.
   * 
   * @param shootX the x value of the target position
   * @param shootY the y value of the target position
   */
  void shoot(int shootX, int shootY) {
    bulletList.add(new PlaneBullet(x, y, shootX, shootY));
    float bulletAngle = bulletList.get(bulletList.size() - 1)
        .getAngle();
    x -= RECOIL * cos(bulletAngle);
    y -= RECOIL * sin(bulletAngle);
  }

  /**
   * Rotates the plane to the left.
   */
  void turnLeft() {
    degrees = degrees - TURN_INCREMENT;
  }

  /**
   * Rotates the plane to the right.
   */
  void turnRight() {
    degrees = degrees + TURN_INCREMENT;
  }

  /**
   * Increases the plane's speed.
   */
  void speedUp() {
    if (speed < MAX_SPEED) {
      speed = speed + SPEED_INCREMENT;
      playerProp.setGain(playerProp.getGain()+.1);
    }
  }

  /**
   * Decreases the plane's speed.
   */
  void slowDown() {
    if (speed > MIN_SPEED) {
      speed = speed - SPEED_INCREMENT;
      playerProp.setGain(playerProp.getGain()-.1);
    }
  }
  
  void resetSmoke() {
    this.smokeList.clear();
  }

  /**
   * Toggles the smoke trail behind the plane on or off.
   */
  void toggleSmoke() {
    if (smoke == false) {
      smoke = true;
    } else {
      smoke = false;
    }
  }

  /**
   * Updates the plane once per frame. Update() is called by
   * the main class, and handles anything that must be called
   * once per frame but is not related to the GUI.
   */
  void update() {
    angle = (float) (2 * Math.PI * (degrees / 360));
    x = x + speed * cos(angle);
    y = y + speed * sin(angle);
    gunX = x + planeDimensions/4;
    gunY = y;

    //Spawns smoke.
    if (smoke) {
      if (smokeTimer <= 0) {
        smokeList.add(new Smoke(x, y, lives));
        smokeTimer = 10;
      } else {
        smokeTimer--;
      }
    }

    //Updates bullets.
    for (int n = 0; n < bulletList.size(); n++) {
      bulletList.get(n).update();
      if (bulletList.get(n).outOfBounds()) {
        bulletList.remove(n);
        n--;
      }
    }
  }
  
  public ArrayList<Smoke> getSmokeList() {
   return this.smokeList; 
  }

  /**
   * Draws the plane. Also calls the draw() method on any smoke
   * and bullet objects that are currently spawned.
   */
  void draw() {
    //Draws smoke.
    stroke(0, 0, 0, 0);
    for (int n = 0; n < smokeList.size(); n++) {
      smokeList.get(n).draw();
    }
    for (int n = 0; n < bulletList.size(); n++) {
      bulletList.get(n).draw();
    }

    //Rotates the plane and changes the current frame.
    translate(x, y);
    rotate((float) Math.PI + angle);
    if (millis() - time >= wait) {
      countIm++;
      planeImgAdjust = switchIm();
      time = millis();//also update the stored time
      wait = propellarSpeed/plane.getSpeed();
    }
    if (planeImgAdjust.equals(planeImg)) {
      image(planeImgAdjust, - planeImg.width/2, - planeImg.height/2 - 3);
    }
    else {
      image(planeImgAdjust, - planeImg.width/2, - planeImg.height/2);
    }
    rotate(- ((float) Math.PI + angle));
    translate(-x, -y);
  }
  
  /**
   * A helper method for draw() that determines which image of the plane
   * should be displayed.
   *
   * @return the image that should currently be displayed
   */
  PImage switchIm() {
    switch(countIm) {
      case 0:
        return planeImg;
      case 1:
        return planeImg2;
      case 2:
        return planeImg3;
      case 3:
        return planeImg2;
      case 4:
        return planeImg;
      case 5:
        return planeImg2;
      case 6:
        return planeImg3;
      case 7:
        countIm = -1;
        return planeImg2;
    }
    return planeImg; 
  }
  
  int getLives() {
    return this.lives; 
  }
  
  void minusLife() {
    this.lives = this.lives - 1;
    System.out.println(this.lives);
    if (lives == 0) {
      gameOver = true;
    }
  }

  /**
   * Gets the plane's current x position.
   *
   * @return the x position as a float
   */
  float getX() {
    return this.x; 
  }

  /**
   * Gets the plane's current y position.
   *
   * @return the x position as a float
   */
  float getY() {
    return this.y; 
  }

  /**
   * Gets the gun's current x position.
   *
   * @return the x position as a float
   */
  float getGunX() {
    return this.gunX; 
  }

  /**
   * Gets the gun's current y position.
   *
   * @return the x position as a float
   */
  float getGunY() {
    return this.gunY; 
  }
  
  ArrayList<PlaneBullet> getBullets() {
    return this.bulletList; 
  }
  
  double getSpeed() {
   return this.speed; 
  }
}

