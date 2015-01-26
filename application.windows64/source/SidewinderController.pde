public class SidewinderController {
  ArrayList<Sidewinder> sidewinders;
  ArrayList<PlaneBullet> bulletList;

  float difficultyClock = millis();
  float difficultyDelay = 6000.0;
  
  float TIME_INCREMENT = 250.0;
  float MIN_TIME = 500.0;
  int STARTING_TIME = 2000;

  public SidewinderController() {
    wait2 = STARTING_TIME;
    sidewinders = new ArrayList<Sidewinder>();
    sidewinders.add(new Sidewinder());
    bulletList = plane.getBullets();
  }

  void removeSidewinder(Sidewinder sidewinder) {
    sidewinders.remove(sidewinder);
  }

  void update() {
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
  
  void draw() {
    for (int n = 0; n < sidewinders.size(); n++) {
      sidewinders.get(n).draw();
    }
  }
}
