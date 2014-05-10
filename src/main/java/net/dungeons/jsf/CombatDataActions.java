package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.CombatMap;
import net.dungeons.model.Combatant;
import net.dungeons.model.StatusEffect;

@RequestScoped
@Named("combatDataActions")
public class CombatDataActions {

  @Inject
  private SessionData data;
  private Combatant combatant = new Combatant();

  public String create(boolean visible) {
    data.getCombat().addCombatant(combatant, visible);
    return null;
  }

  public String remove(String combatant) {
    data.getCombat().removeCombatant(combatant);
    return null;
  }

  public String hide(String combatant) {
    data.getCombat().hideCombatant(combatant);
    return null;
  }

  public String show(String combatant) {
    data.getCombat().showCombatant(combatant);
    return null;
  }

  public String update() {
    data.getCombat().updateCombatants();
    return null;
  }

  public String updateCombatMap(CombatMap combatMap) {
    data.getCombat().setCombatMap(combatMap);
    return null;
  }
  
  public Combatant getCombatant() {
    return combatant;
  }

  public void setCombatant(Combatant combatant) {
    this.combatant = combatant;
  }
}
