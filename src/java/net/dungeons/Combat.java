package net.dungeons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Combat {

  private List<String> initiatives;
  private final List<Combatant> combatants;
  private final List<CombatLog> combatLog;
  private final CombatListenerController listeners;

  public Combat() {
    listeners = new CombatListenerController();
    combatants = new LinkedList<>();
    combatLog = new LinkedList<>();
    recalculateInitiatives();
  }

  public List<String> getInitiatives() {
    return new ArrayList<>(initiatives);
  }

  public void nextInitiative() {
    List<String> initiativesCopy = initiatives;
    if (initiativesCopy.size() > 0) {
      initiativesCopy.add(initiativesCopy.remove(0));
      listeners.sendInitiativeUpdated(getInitiatives());
    }
  }

  public void previousInitiative() {
    List<String> initiativesCopy = initiatives;
    if (initiativesCopy.size() > 0) {
      initiativesCopy.add(0, initiativesCopy.remove(initiativesCopy.size() - 1));
      listeners.sendInitiativeUpdated(getInitiatives());
    }
  }

  public void initiativeJumpTo(String name) {
    List<String> initiativesCopy = initiatives;
    if (name != null) {
      while (initiativesCopy.contains(name) && !initiativesCopy.get(0).equals(name)) {
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
    Combatant oldCombatant = getCombatant(combatantId);
    if (oldCombatant != null) {
      listeners.sendCombatantUpdated(oldCombatant);
      recalculateInitiatives();
    }

  }

  public void addCombatant(Combatant combatant) {
    Combatant oldCombatant = getCombatant(combatant.getName());
    if (oldCombatant == null) {
      combatants.add(combatant);
      listeners.sendCombatantAdded(combatant);
      recalculateInitiatives();
    }
  }

  public void removeCombatant(String combatantId) {
    Combatant oldCombatant = getCombatant(combatantId);
    if (oldCombatant != null) {
      combatants.remove(oldCombatant);
      listeners.sendCombatantRemoved(oldCombatant);
      recalculateInitiatives();
    }
  }

  public Combatant getCombatant(String name) {
    for (Combatant combatant : combatants) {
      if (combatant.getName().equals(name)) {
        return combatant;
      }
    }
    return null;
  }

  public String getCurrent() {
    List<String> initiativesCopy = initiatives;
    if (initiativesCopy != null && initiativesCopy.size() > 0) {
      return initiativesCopy.get(0);
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
    List<Combatant> tempCombatants = getInitiativesRaw();
    List<String> tempInitiatives = new LinkedList<>();
    for (Combatant c : tempCombatants) {
      tempInitiatives.add(c.getName());
    }
    if (current != null) {
      initiativeJumpTo(current);
    }
    listeners.sendInitiativeUpdated(new ArrayList<>(tempInitiatives));
    this.initiatives = tempInitiatives;
  }

  public List<Combatant> getCombatants() {
    return combatants;
  }

  public List<CombatLog> getCombatLog() {
    return combatLog;
  }

  private List<Combatant> getInitiativesRaw() {
    List<Combatant> tempCombatants = new LinkedList<>(combatants);
    Collections.sort(tempCombatants, new Comparator<Combatant>() {
      @Override
      public int compare(Combatant o1, Combatant o2) {
        return o2.getInitiative() - o1.getInitiative();
      }
    });
    return tempCombatants;
  }
}
