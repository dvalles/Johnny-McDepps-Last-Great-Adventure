/**
 * An object that represents a missile.
 * @author Austin French and Dom Valles
 */
public class Missile {

ArrayList<Missile> missiles = new ArrayList<Missile>();
float posx,posy;
float xChange, yChange;
int frequency;


public Missile() {
  //this.posx = (int) random((displayWidth/2)-(WIDTH/2),
  //(displayWidth/2)-(WIDTH/2) + WIDTH);
  this.posx = random(0, WIDTH);
  this.posy = (displayHeight/12) + HEIGHT + bulletDim;
}

void missileUpdate() {
   if (millis() - time2 >= wait2) {
      missiles.add(new Missile());
      time2 = millis();//also update the stored time
    }
    
    updateList(missiles);
    
    for (Missile miss: missiles) {
      miss.setY(1);
      image(missileImg, miss.getX(), miss.getY());
    }
}

void updateList(ArrayList<Missile> list) {
  
 //check to see if out of bounds
 for (int x = 0; x < list.size(); x++) {
  if (list.get(x).getX() > WIDTH + missileDim || list.get(x).getX() < 0 - missileDim
  || list.get(x).getY() < 0 - missileDim) {
   list.remove(x); 
  }  
 }
 
 //check to see if bullet has hit missile
 ArrayList<PlaneBullet> bullets = plane.getBullets();
 
 for (int x = 0; x < bullets.size(); x++) {
  
   float xBullet = bullets.get(x).getX();
   float yBullet = bullets.get(x).getY();
  
  for (int y = 0; y < list.size(); y++) {
   
    float xMiss = list.get(y).getX();
    float yMiss = list.get(y).getY();
    
    if ((yBullet + bulletDim > yMiss && yBullet < yMiss + missileDim) &&
        (xBullet + bulletDim > xMiss && xBullet < xMiss + missileDim ))  {
      list.remove(y);
      bullets.remove(x);
    } 
  }
 }
 
 //check to see if missile has hit plane
 for (int x = 0; x < list.size(); x++) {
   float yMiss = list.get(x).getY();
   float xMiss = list.get(x).getX();
   float yPlane = plane.getY();
   float xPlane = plane.getX();
   
  if ((xMiss + missileDim > xPlane + 22 && xMiss < xPlane + planeDimensions - 22) && 
      (yMiss + missileDim > yPlane + 32 && yMiss < yPlane + planeDimensions - 32)) {
   plane.minusLife();
   list.remove(x);
  if (plane.getLives() == 0) {
   gameOver = true;
  } 
  }
 } 
}

void setX(float x) {
  this.posx = this.posx + x;  
}

void setY(float y) {
  this.posy = this.posy - y; 
}

float getX() {
  return this.posx;
}

float getY() {
  return this.posy;
}

//void setOldX(float x) {
//  this.oldxChange = x;
//}

//float getOldX() {
//   return this.oldxChange; 
//}

}
