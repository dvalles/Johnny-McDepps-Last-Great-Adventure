/**
 * The main class for our project.
 * @author Austin French and Dom Valles
 */
import ddf.minim.*;

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

color smokeColor0, smokeColor1, smokeColor2;
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
void setup() {
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
void loadAudio() {
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
void loadImages() {
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
void keyPressed() {
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
void keyReleased() {
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
void mousePressed() {
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
void update() {
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
PImage switchInstructions() {
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
  
  void explosionDrawFrame(Explosion e) {
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
  
  void explosionRemove(ArrayList<Explosion> explosions) {
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
void draw() {
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
