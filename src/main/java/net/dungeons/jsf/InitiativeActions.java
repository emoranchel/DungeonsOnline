package net.dungeons.jsf;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Combatant;

@RequestScoped
@Named("initiativeActions")
public class InitiativeActions {

  @Inject
  private SessionData data;

  public String prevInitiative() {
    data.getCombat().previousInitiative();
    return null;
  }

  public String nextInitiative() {
    data.getCombat().nextInitiative();
    return null;
  }

  public List<Combatant> getInitiatives() {
    return data.getCombat().getInitiatives();
  }
}
