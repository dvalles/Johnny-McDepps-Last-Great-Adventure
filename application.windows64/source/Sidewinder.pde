public class Sidewinder {
  float x;
  float y;
  float deltaX;
  float deltaY;
  float angle;
  float oldTargetAngle;
  float newTargetAngle;
  float deltaTargetAngle;
  float degrees;
  ArrayList<PlaneBullet> bulletList;

  final float TURN_INCREMENT = 0.9;
  final float MAX_OFFSET = (float) Math.PI * (1.0/2.0);
  final float SPEED = 1.8;

  public Sidewinder() {
    x = random(0, WIDTH);
    y = HEIGHT + HEIGHT/12 + bulletDim;
    degrees = 270;
    oldTargetAngle = getTargetAngle();
  }

  float getTargetAngle() {
    float targetAngle;
    deltaX = plane.getX() - x;
    deltaY = plane.getY() - y;
    
    if (deltaX == 0) {
      if (deltaY >= 0) {
        targetAngle = (float) Math.PI * (1.0/2.0);
      } else {
        targetAngle = (float) Math.PI * (3.0/2.0);
      }
    } else {
      float slope = deltaY / deltaX;
      targetAngle = atan(slope);
      if (deltaX < 0) {
        targetAngle = (float) Math.PI + targetAngle;
      }
    }
    return targetAngle;
  }

  void turnLeft() {
    degrees -= TURN_INCREMENT;
  }

  void turnRight() {
    degrees += TURN_INCREMENT;
  }

  void update() {
    angle = (float) (2 * Math.PI * degrees / 360);
    newTargetAngle = getTargetAngle();
    deltaTargetAngle = oldTargetAngle - newTargetAngle;
      
    //Turns towards intercept course.
    float relativeAngle = newTargetAngle - angle;
    if (relativeAngle < - ((float) Math.PI)) {
      relativeAngle += 2 * (float) Math.PI;
    } else if (relativeAngle > (float) Math.PI) {
      relativeAngle -= 2 * (float) Math.PI;
    }
    if (!(relativeAngle > MAX_OFFSET) && !(relativeAngle < (- MAX_OFFSET))) {
      if (deltaTargetAngle > 0) {
        turnLeft();
      } else if (deltaTargetAngle < 0) {
        turnRight();
      }
    }

    //Move.
    x += SPEED * cos(angle);
    y += SPEED * sin(angle);

    oldTargetAngle = newTargetAngle;

    //Bullet collisions.
    for (int n = 0; n < sidewinderController.bulletList.size(); n++) {
      float bulletX = sidewinderController.bulletList.get(n).getX();
      float bulletY = sidewinderController.bulletList.get(n).getY();
      if ((bulletY + bulletDim > y && bulletY < y + missileDim) &&
          (bulletX + bulletDim > x && bulletX < x + missileDim)) {
        sidewinderController.removeSidewinder(this);
         float x = sidewinderController.bulletList.get(n).getX();
         float y = sidewinderController.bulletList.get(n).getY();
        sidewinderController.bulletList.remove(n);
        explosions.add(new Explosion(x-50,y-50,100));
      }
    }
    
    //Plane collisions.
    if ((x + missileDim > plane.getX() + 22 && x < plane.getX() + planeDimensions - 22) && 
        (y + missileDim > plane.getY() + 32 && y < plane.getY() + planeDimensions - 40)) {
      plane.minusLife();
      float x = plane.getX();
      float y = plane.getY();
      explosions.add(new Explosion(x-100,y-100,200));
      sidewinderController.removeSidewinder(this);
    }
  }

  void draw() {
    translate(x, y);
    rotate(1.0/2.0 * (float) Math.PI + angle);
    image(missileImg, 0, 0);
    rotate(- (1.0/2.0 * (float) Math.PI + angle));
    translate(-x, -y);
  }
}
