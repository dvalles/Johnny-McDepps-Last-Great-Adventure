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
  void draw() {
    //fill(smokeColor);
    //ellipse(x, y, smokeThickness, smokeThickness);
    image(localImage, x - 20, y - 20);
  }
}
