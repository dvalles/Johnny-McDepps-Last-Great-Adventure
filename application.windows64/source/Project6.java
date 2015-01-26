import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import java.lang.Math; 
import java.util.ArrayList; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Project6 extends PApplet {

/**
 * The main class for our project.
 * @author Austin French and Dom Valles
 */


boolean sidewinders = true;

boolean frameSet = true;
final int WIDTH = 800;
final int HEIGHT = 600;
final int MAX_INTRO_SLIDES = 5;
int groundHeightFactor = 6;

boolean leftPressed = false;
boolean rightPressed = false;
boolean upPressed = false;
boolean downPressed = false;

AudioPlayer player;
Minim minim;
AudioPlayer playerProp;
int fadeCount1 = 0;
int propCount = 0;
AudioPlayer playerGame;
int gameCount = 0;
AudioPlayer playerSlides;
int slidesCount = 0;
AudioPlayer playerClick;
int playerClickCount = 0;

PImage planeImg, planeImg2, planeImg3, planeImgAdjust;
int planeDimensions = 70;
PImage crosshair;
int crosshairDimensions = 40;
int crosshairOpacity = 127;
PImage bulletImg;
int bulletDim = 20;
PImage background;
PImage missileImg;
int missileDim = 37;
PImage everest;
PImage panel1, panel2, panel3, panel4, panel5;
PImage cloud1, cloud2, cloud3;
PImage startScreen, startScreen2;
PImage instructions1, instructions2, instructions3;
PImage rain1,rain2,rain3;
PImage smokeImg1, smokeImg2, smokeImg3;
PImage explosion1, explosion2, explosion3, explosion4, explosion5, explosion6, explosion7;

int countIm = -1;
double time;
double wait = 20;

boolean waitIntroBool = true;
double waitIntro = 733;
double time3;

double time2;
double wait2 = 6000;
double propellarSpeed = 150;

boolean gameOver = false;
boolean intro = true;
int introSlide = -2;

//Plane plane = new Plane(WIDTH/2, HEIGHT/2);
Plane plane = new Plane(WIDTH + 40, HEIGHT/3);
Clouds cloud = new Clouds();
ArrayList<Clouds> cloudsBehind = new ArrayList<Clouds>();
ArrayList<Clouds> cloudsInFront = new ArrayList<Clouds>();
ArrayList<Clouds> clouds = new ArrayList<Clouds>();
int cloudFrontorBehind = 0;

Missile missile;
SidewinderController sidewinderController;

int smokeColor0, smokeColor1, smokeColor2;
int smokeThickness;

int countInstructions = 0;
PImage tempInstruct;
double time4 = 0;
double waitInstruct = 50;

ArrayList<Explosion> explosions = new ArrayList<Explosion>();
double timeExplosion;
double waitExplosion = 20;


/**
 * Sets up the screen.
 */
public void setup() {
  size(WIDTH, HEIGHT);
  loadAudio();
  loadImages();
  player.loop();
  time = millis();
  time2 = millis();
  smokeColor0 = color(250, 250, 250);
  smokeColor1 = color(250, 200, 150);
  smokeColor2 = color(250, 150, 50);
  smokeThickness = 20;

  sidewinderController = new SidewinderController();

}

//loads all the audio
public void loadAudio() {
   minim = new Minim(this);
  player = minim.loadFile("Piano.mp3", 2048);
  playerProp = minim.loadFile("propellar.mp3", 2048);
  playerGame = minim.loadFile("Wombat.mp3", 2048);
  playerSlides = minim.loadFile("Main_Menu_Music.mp3", 2048);
  playerClick = minim.loadFile("M4A1.mp3", 2048);
  playerProp.setLoopPoints(3000,16000);
  //playerClick.setLoopPoints(800,1000);
  playerClick.setGain(-10);
  playerGame.setGain(-5);
}

/**
 * Loads all images.
 */
public void loadImages() {
  planeImg  = loadImage("tiny-airplane.png");
  planeImg.resize(planeDimensions, planeDimensions);
  planeImg2 = loadImage("frame-2-and-4.png");
  planeImg2.resize(planeDimensions, planeDimensions);
  planeImg3 = loadImage("frame-3.png");
  planeImg3.resize(planeDimensions, planeDimensions);
  planeImgAdjust = planeImg;
  crosshair = loadImage("crosshair-pixelated.png");
  crosshair.resize(crosshairDimensions, crosshairDimensions);
  bulletImg = loadImage("missile-with-lines.png");
  bulletImg.resize(bulletDim, bulletDim);
  background = loadImage("textured-background.jpg");
  background.resize(WIDTH, HEIGHT);
  missileImg = loadImage("bullet2-with-flames.png");
  //everest = loadImage("everest.jpg");
  //everest.resize(WIDTH, HEIGHT);
  panel1 = loadImage("panel1_color.jpg");
  panel2 = loadImage("panel2-color.jpg");
  panel3 = loadImage("panel3-color.jpg");
  panel4 = loadImage("panel4--color.jpg");
  panel5 = loadImage("panel5-color.jpg");
  cloud1 = loadImage("Cloud1.png");
  cloud2 = loadImage("Cloud2.png");
  cloud3 = loadImage("Cloud3.png");
  startScreen = loadImage("start-screen.jpg");
  startScreen2 = loadImage("start-screen-frame2.jpg");
  instructions1 = loadImage("how-to-play-frame1.jpg");
  instructions2 = loadImage("how-to-play-frame2&4.jpg");
  instructions3 = loadImage("how-to-play-frame3.jpg");
  tempInstruct = instructions1;
  smokeImg1 = loadImage("GSsecond-hit-100.png");
  smokeImg2 = loadImage("airplane-smoke-100.png");
  smokeImg3 = loadImage("airplane-smoke-50.png");
  explosion1 = loadImage("1.png");
  explosion2 = loadImage("2.png");
  explosion3 = loadImage("3.png");
  explosion4 = loadImage("4.png");
  explosion5 = loadImage("5.png");
  explosion6 = loadImage("6.png");
  explosion7 = loadImage("7.png");
}

/**
 * The method that is called whenever a key is pressed. It checks
 * which key it was and then carries out the appropriate action.
 */
public void keyPressed() {
  if (!intro) {
    if (keyCode == LEFT) {
      leftPressed = true;
    }
    if (keyCode == RIGHT) {
      rightPressed = true;
    }
    if (keyCode == UP) {
      upPressed = true;
    }
    if (keyCode == DOWN) {
      downPressed = true;
    }
    if (key == ' ') {
      plane.toggleSmoke();
    }
    if (key == 'r') {
      plane.resetSmoke();
    }
  }
}

/**
 * The method that is called whenever a key is released. It checks
 * which key it was and then carries out the appropriate action.
 */
public void keyReleased() {
  if (!intro) {
    if (keyCode == LEFT) {
      leftPressed = false;
    }
    if (keyCode == RIGHT) {
      rightPressed = false;
    }
    if (keyCode == UP) {
      upPressed = false;
    }
    if (keyCode == DOWN) {
      downPressed = false;
    }
  }
}

/**
 * The method that is called whenever the mouse is clicked. It
 * instructs the plane to spawn a bullet targeting the current
 * position of the mouse.
 */
public void mousePressed() {
  if (!intro) {
    playerClick.play();
    playerClick.rewind();
    plane.shoot(mouseX, mouseY);
  } else {
    introSlide++;
    if (introSlide == MAX_INTRO_SLIDES) {
      intro = false;
    }
  }
}

/**
 * Updates the project once per frame. Update is called by the
 * draw() method, and handles any actions that need to happen
 * once per frame but are unrelated to the GUI.
 */
public void update() {
  if (!intro) {
    if (leftPressed) {
      plane.turnLeft();
    }
    if (rightPressed) {
      plane.turnRight();
    }
    if (upPressed) {
      plane.speedUp();
    }
    if (downPressed) {
      plane.slowDown();
    }
    plane.update();
    if (sidewinders) {
      sidewinderController.update();
    }
  }
}

/**
* function to switch through Instructional screens;
*/
public PImage switchInstructions() {
    switch(countInstructions) {
      case 0:
        return instructions1;
      case 1:
        return instructions2;
      case 2:
        return instructions3;
      case 3:
        return instructions2;
      case 4:
        return instructions1;
      case 5:
        return instructions2;
      case 6:
        return instructions3;
      case 7:
        countInstructions = -1;
        return instructions2;
    }
    return instructions1; 
  }
  
  public void explosionDrawFrame(Explosion e) {
    switch(e.getFrame()) {
      case 0:
        image(explosion1, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 1:
        image(explosion2, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 2:
        image(explosion3, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 3:
        image(explosion4, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 4:
        image(explosion5, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 5:
        image(explosion6, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 6:
        image(explosion7, e.getX(), e.getY(), e.getSize(), e.getSize());
        break;
      case 7:
        //explosions.remove(e);
        break;
     // case 8:
      //  explosions.remove(e);
    }
    e.incrementFrame();
  }
  
  public void explosionRemove(ArrayList<Explosion> explosions) {
   for (Explosion explosion: explosions) {
    if (explosion.getFrame() > 6) {
     //explosions.remove(explosion); 
    }
   } 
  }
  
  public void explosionUpdate() {
   for (Explosion explosion: explosions) {
    explosionDrawFrame(explosion);
   } 
  }

/**
 * Draws the background and crosshair. Also calls update(), and calls draw()
 * on the plane.
 */
public void draw() {
  if (!gameOver && !intro) {
    update();  
    
    image(background, 0, 0);
    
    if (propCount == 0) {
      playerSlides.setGain(-80);
      playerProp.loop();
      playerProp.setGain(-20);
      propCount++;
    }
    
    if (gameCount == 0) {
      playerGame.loop();
      gameCount++;
    }
    
    cloud.cloudUpdate(clouds);
    cloud.removeOffScreen(clouds);
    cloudFrontorBehind = 1;
   
    plane.draw(); 
    
    //update crosshair
    tint(255, crosshairOpacity);
    image(crosshair, mouseX - crosshairDimensions/2, mouseY - crosshairDimensions/2);
    tint(255, 255);
  
    if (frameSet) {
      frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
    }

    sidewinderController.draw();
     
    if (millis() - timeExplosion >= waitExplosion) {
       explosionRemove(explosions); 
       explosionUpdate();
       timeExplosion = millis();//also update the stored time
    } 
   
   
    cloud.cloudUpdate(clouds);
    cloud.removeOffScreen(clouds);
    cloudFrontorBehind = 0;
    
    
  } else if (intro) {
    switch (introSlide) {
      case -2:
        if (millis() - time3 >= waitIntro) {
          if (waitIntroBool) {waitIntroBool = false;}
          else {waitIntroBool = true;}      
          time3 = millis();//
          }
        if (waitIntroBool) {
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));  
        image(startScreen, 0, 0);}
        else {image(startScreen2, 0, 0);}
        break;
      case -1:
        if (millis() - time4 >= waitInstruct) {
        tempInstruct = switchInstructions();    
        time4 = millis();
        countInstructions++;
        }
        if (fadeCount1 == 0) {
        player.shiftGain(player.getGain(), -80, 3000);
        fadeCount1++;
        }
        if (slidesCount == 0) {
         playerSlides.loop();
         playerSlides.shiftGain(-80, 13, 100);
         slidesCount++; 
        }
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        image(tempInstruct, 0, 0);
        break;
      case 0:
        image(panel1, 0, 0);
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        break;
      case 1:
        image(panel2, 0, 0);
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        break;
      case 2:
        image(panel3, 0, 0);
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        break;
      case 3:
        image(panel4, 0, 0);
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        break;
      case 4:
        image(panel5, 0, 0);
        frame.setLocation((displayWidth/2)-(WIDTH/2), (displayHeight/12));
        break;
    }
  } else if (gameOver) {
    image(background, 0, 0);
    stroke(0, 0, 0, 0);
    for (int n = 0; n < plane.getSmokeList().size(); n++) {
      plane.getSmokeList().get(n).draw();
    }
    noLoop();
    //System.out.println("Game Over");
  }
}
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
  return random(1) > .5f; 
 }
 
 public void setX() {
  if (this.LorR) {
   this.posX += .5f;
  } else {
   this.posX += -.5f;
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
 
 public void cloudUpdate(ArrayList<Clouds> clouds) {
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
public class Explosion {
 private float posX;
 private float posY;
 private int size;
 private int frame;
 
  public Explosion(float x, float y, int size) {
  this.posX = x;
  this.posY = y;
  this.size = size;
  this.frame = 0;
 }

 public int getFrame() {
  return this.frame;
 }

 public float getX() {
  return this.posX;
 }

 public float getY() {
  return this.posY;
 }

 public int getSize() {
  return this.size;
 }
  
 public void incrementFrame() {
  this.frame++;
 } 
}
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

public void missileUpdate() {
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

public void updateList(ArrayList<Missile> list) {
  
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

public void setX(float x) {
  this.posx = this.posx + x;  
}

public void setY(float y) {
  this.posy = this.posy - y; 
}

public float getX() {
  return this.posx;
}

public float getY() {
  return this.posy;
}

//void setOldX(float x) {
//  this.oldxChange = x;
//}

//float getOldX() {
//   return this.oldxChange; 
//}

}



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

  final float MIN_SPEED = 1.0f;
  final float MAX_SPEED = 10;
  final float SPEED_INCREMENT = 0.09f;
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
  public void shoot(int shootX, int shootY) {
    bulletList.add(new PlaneBullet(x, y, shootX, shootY));
    float bulletAngle = bulletList.get(bulletList.size() - 1)
        .getAngle();
    x -= RECOIL * cos(bulletAngle);
    y -= RECOIL * sin(bulletAngle);
  }

  /**
   * Rotates the plane to the left.
   */
  public void turnLeft() {
    degrees = degrees - TURN_INCREMENT;
  }

  /**
   * Rotates the plane to the right.
   */
  public void turnRight() {
    degrees = degrees + TURN_INCREMENT;
  }

  /**
   * Increases the plane's speed.
   */
  public void speedUp() {
    if (speed < MAX_SPEED) {
      speed = speed + SPEED_INCREMENT;
      playerProp.setGain(playerProp.getGain()+.1f);
    }
  }

  /**
   * Decreases the plane's speed.
   */
  public void slowDown() {
    if (speed > MIN_SPEED) {
      speed = speed - SPEED_INCREMENT;
      playerProp.setGain(playerProp.getGain()-.1f);
    }
  }
  
  public void resetSmoke() {
    this.smokeList.clear();
  }

  /**
   * Toggles the smoke trail behind the plane on or off.
   */
  public void toggleSmoke() {
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
  public void update() {
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
  public void draw() {
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
  public PImage switchIm() {
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
  
  public int getLives() {
    return this.lives; 
  }
  
  public void minusLife() {
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
  public float getX() {
    return this.x; 
  }

  /**
   * Gets the plane's current y position.
   *
   * @return the x position as a float
   */
  public float getY() {
    return this.y; 
  }

  /**
   * Gets the gun's current x position.
   *
   * @return the x position as a float
   */
  public float getGunX() {
    return this.gunX; 
  }

  /**
   * Gets the gun's current y position.
   *
   * @return the x position as a float
   */
  public float getGunY() {
    return this.gunY; 
  }
  
  public ArrayList<PlaneBullet> getBullets() {
    return this.bulletList; 
  }
  
  public double getSpeed() {
   return this.speed; 
  }
}

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

  final float TURN_INCREMENT = 0.9f;
  final float MAX_OFFSET = (float) Math.PI * (1.0f/2.0f);
  final float SPEED = 1.8f;

  public Sidewinder() {
    x = random(0, WIDTH);
    y = HEIGHT + HEIGHT/12 + bulletDim;
    degrees = 270;
    oldTargetAngle = getTargetAngle();
  }

  public float getTargetAngle() {
    float targetAngle;
    deltaX = plane.getX() - x;
    deltaY = plane.getY() - y;
    
    if (deltaX == 0) {
      if (deltaY >= 0) {
        targetAngle = (float) Math.PI * (1.0f/2.0f);
      } else {
        targetAngle = (float) Math.PI * (3.0f/2.0f);
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

  public void turnLeft() {
    degrees -= TURN_INCREMENT;
  }

  public void turnRight() {
    degrees += TURN_INCREMENT;
  }

  public void update() {
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

  public void draw() {
    translate(x, y);
    rotate(1.0f/2.0f * (float) Math.PI + angle);
    image(missileImg, 0, 0);
    rotate(- (1.0f/2.0f * (float) Math.PI + angle));
    translate(-x, -y);
  }
}
public class SidewinderController {
  ArrayList<Sidewinder> sidewinders;
  ArrayList<PlaneBullet> bulletList;

  float difficultyClock = millis();
  float difficultyDelay = 6000.0f;
  
  float TIME_INCREMENT = 250.0f;
  float MIN_TIME = 500.0f;
  int STARTING_TIME = 2000;

  public SidewinderController() {
    wait2 = STARTING_TIME;
    sidewinders = new ArrayList<Sidewinder>();
    sidewinders.add(new Sidewinder());
    bulletList = plane.getBullets();
  }

  public void removeSidewinder(Sidewinder sidewinder) {
    sidewinders.remove(sidewinder);
  }

  public void update() {
    // Update Sidewinders.
    if (millis() - time2 >= wait2) {
      sidewinders.add(new Sidewinder());
      time2 = millis();//also update the stored time
    }

    // Update Sidewinder spawn rate.
    if (millis() - difficultyClock >= difficultyDelay) {
      difficultyClock = millis();
      if (wait2 > MIN_TIME) {
        wait2 -= TIME_INCREMENT;
      }
      println(wait2);
    }

    // Calls Sidewinder update methods.
    for (int n = 0; n < sidewinders.size(); n++) {
      sidewinders.get(n).update();
    }
  }
  
  public void draw() {
    for (int n = 0; n < sidewinders.size(); n++) {
      sidewinders.get(n).draw();
    }
  }
}
/**
 * A class that represents a single puff of smoke.
 * @author Austin French and Dom Valles
 */
public class Smoke {
  float x;
  float y;
  PImage localImage;

  /**
   * A constructor for the Smoke object.
   *
   * @param inputX the x position of the smoke
   * @param inputY the y position of the smoke
   */
  public Smoke(float inputX, float inputY, int inputLives) {
    x = inputX;
    y = inputY;
    if (inputLives == 2) {
      localImage = smokeImg2;
    } else if (inputLives <= 1) {
      localImage = smokeImg1;
    } else {
      localImage = smokeImg3;
    }
  }

  /**
   * Draws the smoke at the appropriate location.
   */
  public void draw() {
    //fill(smokeColor);
    //ellipse(x, y, smokeThickness, smokeThickness);
    image(localImage, x - 20, y - 20);
  }
}
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
  public boolean outOfBounds() {
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
  public float getAngle() {
    return angle;
  }

  /**
   * Updates the bullet once per frame. Called by draw(), and
   * handles any actions that need to be done once per frame 
   * but are not related to the GUI.
   */
  public void update() {
    x = x + SPEED * cos(angle);
    y = y + SPEED * sin(angle);
  }

  /**
   * Draws the bullet.
   */
  public void draw() {
    translate(x, y);
    rotate(1.0f/2.0f * (float) Math.PI + angle);
    image(bulletImg, - bulletDim/2.0f, - bulletDim/2.0f);
    rotate(- (1.0f/2.0f * (float) Math.PI + angle));
    translate(-x, -y);
  }
  
  public float getX() {
    return this.x;
  }
  
  public float getY() {
    return this.y;
  }

}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Project6" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
