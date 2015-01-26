public class Clouds {
 
 private float posX;
 private float posY;
 private boolean LorR;
 private int size;
 private int cloudNum;
 private PImage cloudLook;
 
 //constructor
 public Clouds(float x, float y, boolean LorR, int size, int cloudNum) {
   this.posX = x;
   this.posY = y;
   this.LorR = LorR;
   this.size = size;
   this.cloudLook = cloudLook;
   this.cloudNum = cloudNum;
   if (cloudNum == 1) {
    this.cloudLook = cloud1; 
   } else if (cloudNum == 2) {
     this.cloudLook = cloud2;
   } else {
     this.cloudLook = cloud3;
   }
    
 }
 
 public Clouds() {}
 
 public float getX() {
  return this.posX; 
 }
 
 public float getY() {
  return this.posY;
 }
 
 //figure out what cloud image it uses
 public int getCloudNum() {
  return this.cloudNum; 
 }
 
 //same as above I think
 public PImage getCloudLook() {
  return this.cloudLook; 
 }
 
 public boolean getLorR() {
  return this.LorR; 
 }
 
 private boolean randomBoolean() {
  return random(1) > .5; 
 }
 
 public void setX() {
  if (this.LorR) {
   this.posX += .5;
  } else {
   this.posX += -.5;
  } 
 }
 
 public int getSize() {
  return this.size; 
 }
 
 //returns a boundary based off what side it is going towards
 public int findSide(boolean t) {
  if (t) {
   return -200;
  } else {
   return 800;
  } 
 }
 
// public float getZ() {
//   return this.posZ;
// }
 
 void cloudUpdate(ArrayList<Clouds> clouds) {
   //add clouds based off small percentage chance
   double rand = random(3000);
   if (rand < 2) {
    int num = (int) random(1,4);
    boolean LorR = randomBoolean();
    Clouds cloud = new Clouds(findSide(LorR), random(-100, 580), LorR, (int) random(40,200), num);
    
    //check whether the cloud should go in front
    // of plane or behind
    if (cloud.getSize() < 130) {
    cloudsBehind.add(cloud);
    } else {
    cloudsInFront.add(cloud);
    }
   }
  
   if (cloudFrontorBehind == 0) {clouds = cloudsBehind;}
   else {clouds = cloudsInFront;}
  
  // run through and paint clouds
   for (Clouds cloud: clouds) {
     float s = (float) cloud.getSize();
     if (cloud.getCloudNum() == 1) {
       image(cloud1, cloud.getX(), cloud.getY(), s, s);
     } else if (cloud.getCloudNum() == 2) {
       image(cloud2, cloud.getX(), cloud.getY(), s, s);
     } else {
       image(cloud3, cloud.getX(), cloud.getY(), s, s);
     }
     cloud.setX();
     
   }
 }
 
 //remove from list if off screen
 public void removeOffScreen(ArrayList<Clouds> clouds) {
  for (Clouds cloud: clouds) {
   if (cloud.getLorR() && cloud.getX() > 800) {
    clouds.remove(cloud); 
   }
   if (!cloud.getLorR() && cloud.getX() + cloud.getSize() < 0) {
    clouds.remove(cloud); 
   }
  } 
 }
  
}
