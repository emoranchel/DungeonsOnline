package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named("combatMapSetupActions")
public class CombatMapSetupActions {

//  @Inject
//  private SessionData data;

  public void toggle(int x, int y) {
    System.out.println("Cell" + x + "," + y);
  }
  public void toggleUp(int x, int y) {
    System.out.println("UP" + x + "," + y);
  }

  public void toggleDown(int x, int y) {
    System.out.println("DOWN" + x + "," + y);
  }

  public void toggleLeft(int x, int y) {
    System.out.println("LEFT" + x + "," + y);
  }

  public void toggleRight(int x, int y) {
    System.out.println("RIGHT" + x + "," + y);
  }

  public void toggleAll(int x, int y) {
    System.out.println("ALL" + x + "," + y);
  }
}
