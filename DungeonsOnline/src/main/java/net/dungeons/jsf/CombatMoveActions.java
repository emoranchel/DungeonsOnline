package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;

@RequestScoped
@Named("combatMoveActions")
public class CombatMoveActions {

  @Inject
  private SessionData data;
  private int x;
  private int y;
  private String target;

  public String moveRight(Combatant character) {
    character.setX(character.getX() + 1);
    data.getCombat().updateCombatant(character.getName());
    return null;
  }

  public String moveLeft(Combatant character) {
    character.setX(character.getX() - 1);
    data.getCombat().updateCombatant(character.getName());
    return null;
  }

  public String moveUp(Combatant character) {
    character.setY(character.getY() - 1);
    data.getCombat().updateCombatant(character.getName());
    return null;
  }

  public String moveDown(Combatant character) {
    character.setY(character.getY() + 1);
    data.getCombat().updateCombatant(character.getName());
    return null;
  }

  public String move() {
    Combat combat = data.getCombat();
    Combatant combatant = combat.getCombatants().get(target);
    if (combatant != null) {
      combatant.setX(x);
      combatant.setY(y);
      combat.updateCombatant(combatant.getName());
    }
    return null;
  }

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
}
