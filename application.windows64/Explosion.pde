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
