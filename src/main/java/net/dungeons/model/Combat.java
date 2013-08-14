package net.dungeons.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import net.dungeons.model.listeners.CombatListener;
import net.dungeons.model.listeners.CombatListenerController;

public class Combat implements Serializable {

  //
  private CombatMap combatMap;
  //
  private List<Combatant> initiatives;
  private final Combatants combatants;
  //
  private final CombatListenerController listeners;

  public Combat() {
    combatMap = new CombatMap();
    listeners = new CombatListenerController();
    combatants = new Combatants();
    recalculateInitiatives();
  }

  public List<Combatant> getInitiatives() {
    return new ArrayList<>(initiatives);
  }

  public void nextInitiative() {
    List<Combatant> initiativesCopy = initiatives;
    if (initiativesCopy.size() > 0) {
      initiativesCopy.add(initiativesCopy.remove(0));
      listeners.sendInitiativeUpdated(getInitiatives());
    }
  }

  public void previousInitiative() {
    List<Combatant> initiativesCopy = initiatives;
    if (initiativesCopy.size() > 0) {
      initiativesCopy.add(0, initiativesCopy.remove(initiativesCopy.size() - 1));
      listeners.sendInitiativeUpdated(getInitiatives());
    }
  }

  public void initiativeJumpTo(String name) {
    List<Combatant> initiativesCopy = initiatives;
    if (name != null) {
      while (initiativesContains(initiativesCopy, name) && !initiativesCopy.get(0).getName().equals(name)) {
        initiativesCopy.add(initiativesCopy.remove(0));
      }
      listeners.sendInitiativeUpdated(getInitiatives());
    }
  }

  public void clear() {
    for (Combatant combatant : combatants) {
      listeners.sendCombatantRemoved(combatant);
    }
    combatants.clear();
    recalculateInitiatives();
  }

  public void updateCombatants() {
    for (Combatant c : combatants) {
      listeners.sendCombatantUpdated(c);
    }
    recalculateInitiatives();
  }

  public void updateCombatant(String combatantId) {
    listeners.sendCombatantUpdated(combatants.getCombatant(combatantId));
    recalculateInitiatives();
  }

  public void hideCombatant(String combatantId) {
    listeners.sendCombatantRemoved(combatants.hide(combatantId));
    recalculateInitiatives();
  }

  public void showCombatant(String combatantId) {
    listeners.sendCombatantAdded(combatants.show(combatantId));
    recalculateInitiatives();
  }

  public void addCombatant(Combatant combatant, boolean visible) {
    Combatant addedCombatant = visible ? combatants.addVisible(combatant) : combatants.addHidden(combatant);
    listeners.sendCombatantAdded(addedCombatant);
    recalculateInitiatives();
  }

  public void removeCombatant(String combatantId) {
    Combatant combatant = combatants.remove(combatantId);
    listeners.sendCombatantRemoved(combatant);
    recalculateInitiatives();
  }

  public String getCurrent() {
    List<Combatant> initiativesCopy = initiatives;
    if (initiativesCopy != null && initiativesCopy.size() > 0) {
      return initiativesCopy.get(0).getName();
    }
    return null;
  }

  public void addListener(CombatListener combatListener) {
    listeners.addListener(combatListener);
  }

  public void removeListener(CombatListener combatListener) {
    listeners.removeListener(combatListener);
  }

  private void recalculateInitiatives() {
    String current = getCurrent();
    this.initiatives = getInitiativesRaw();
    if (current != null) {
      initiativeJumpTo(current);
    }
    listeners.sendInitiativeUpdated(new ArrayList<>(this.initiatives));
  }

  private List<Combatant> getInitiativesRaw() {
    List<Combatant> tempCombatants = new LinkedList<>(combatants.getCombatants());
    Collections.sort(tempCombatants, new Comparator<Combatant>() {
      @Override
      public int compare(Combatant o1, Combatant o2) {
        return o2.getInitiative() - o1.getInitiative();
      }
    });
    return tempCombatants;
  }

  private boolean initiativesContains(List<Combatant> initiativesCopy, String name) {
    for (Combatant c : initiativesCopy) {
      if (c.getName().equals(name)) {
        return true;
      }
    }
    return false;

  }

  public CombatMap getCombatMap() {
    return combatMap;
  }

  public void setCombatMap(CombatMap combatMap) {
    this.combatMap = combatMap;
    listeners.sendCombatMapUpdated(combatMap);
  }

  public Combatants getCombatants() {
    return combatants;
  }
}
