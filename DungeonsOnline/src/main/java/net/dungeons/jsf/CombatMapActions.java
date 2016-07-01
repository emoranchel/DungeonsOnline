package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Combatant;

@RequestScoped
@Named("combatMapActions")
public class CombatMapActions {

  @Inject
  private SessionData data;
  private int x;
  private int y;
  private Combatant combatant = new Combatant();

  public String create(boolean visible) {
    combatant.setX(x);
    combatant.setY(y);
    data.getCombat().addCombatant(combatant, visible);
    return null;
  }
}
