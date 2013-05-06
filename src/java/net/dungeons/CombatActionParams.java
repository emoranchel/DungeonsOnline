package net.dungeons;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named("combatActionParams")
public class CombatActionParams {

  private int x;
  private int y;
  private String target;
  private Combatant newCombatant = new Combatant();

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public Combatant getNewCombatant() {
    return newCombatant;
  }

  public void setNewCombatant(Combatant newCombatant) {
    this.newCombatant = newCombatant;
  }
}
