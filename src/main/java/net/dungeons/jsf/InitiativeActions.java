package net.dungeons.jsf;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Combatant;
import net.dungeons.model.TurnActions;

@RequestScoped
@Named("initiativeActions")
public class InitiativeActions {

  @Inject
  private SessionData data;
  @Inject
  private TurnActions turnActions;

  public String prevInitiative() {
    data.getCombat().previousInitiative();
    turnActions.reset();
    return null;
  }

  public String nextInitiative() {
    data.getCombat().nextInitiative();
    turnActions.reset();
    return null;
  }

  public List<Combatant> getInitiatives() {
    return data.getCombat().getInitiatives();
  }
}
