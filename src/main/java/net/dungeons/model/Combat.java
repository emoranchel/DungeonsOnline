package net.dungeons.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.dungeons.model.listeners.CombatListener;
import net.dungeons.model.listeners.CombatListenerController;
import net.dungeons.model.listeners.ListenerCollection;

public class Combat implements Serializable, ListenerCollection<CombatListener> {

  //
  private CombatMap combatMap;
  //
  private List<Combatant> initiatives;
  private final Combatants combatants;
  //
  private final CombatListener listeners;
  private final ListenerCollection<CombatListener> listenersC;

  public Combat() {
    initiatives = new ArrayList<>();
    combatMap = new CombatMap();
    final CombatListenerController combatListenerController = new CombatListenerController();
    listenersC = combatListenerController;
    listeners = combatListenerController;
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
      listeners.initiativeUpdated(getInitiatives());
    }
  }

  public void previousInitiative() {
    List<Combatant> initiativesCopy = initiatives;
    if (initiativesCopy.size() > 0) {
      initiativesCopy.add(0, initiativesCopy.remove(initiativesCopy.size() - 1));
      listeners.initiativeUpdated(getInitiatives());
    }
  }

  public void initiativeJumpTo(String name) {
    List<Combatant> initiativesCopy = initiatives;
    if (name != null) {
      while (initiativesContains(initiativesCopy, name) && !initiativesCopy.get(0).getName().equals(name)) {
        initiativesCopy.add(initiativesCopy.remove(0));
      }
      listeners.initiativeUpdated(getInitiatives());
    }
  }

  public void clear() {
    boolean empty = true;
    for (Combatant combatant : combatants) {
      listeners.combatantRemoved(combatant);
      empty = false;
    }
    if (!empty) {
      combatants.clear();
      recalculateInitiatives();
    }
  }

  public void updateCombatants() {
    for (Combatant c : combatants) {
      listeners.combatantUpdated(c);
    }
    recalculateInitiatives();
  }

  public void updateCombatant(String combatantId) {
    listeners.combatantUpdated(combatants.getCombatant(combatantId));
    recalculateInitiatives();
  }

  public void hideCombatant(String combatantId) {
    listeners.combatantRemoved(combatants.hide(combatantId));
    recalculateInitiatives();
  }

  public void showCombatant(String combatantId) {
    listeners.combatantAdded(combatants.show(combatantId));
    recalculateInitiatives();
  }

  public void addCombatant(Combatant combatant, boolean visible) {
    Combatant addedCombatant = visible ? combatants.addVisible(combatant) : combatants.addHidden(combatant);
    if (visible) {
      listeners.combatantAdded(addedCombatant);
    }
    recalculateInitiatives();
  }

  public void removeCombatant(String combatantId) {
    Combatant combatant = combatants.remove(combatantId);
    listeners.combatantRemoved(combatant);
    recalculateInitiatives();
  }

  public String getCurrent() {
    return initiatives.stream().map(Combatant::getName).findFirst().orElse(null);
  }

  @Override
  public void addListener(CombatListener combatListener) {
    listenersC.addListener(combatListener);
  }

  @Override
  public void removeListener(CombatListener combatListener) {
    listenersC.removeListener(combatListener);
  }

  private void recalculateInitiatives() {
    String current = getCurrent();
    this.initiatives = getInitiativesRaw();
    if (current != null) {
      initiativeJumpTo(current);
    }else{
      listeners.initiativeUpdated(new ArrayList<>(this.initiatives));
    }
  }

  private List<Combatant> getInitiativesRaw() {
    List<Combatant> tempCombatants = new LinkedList<>(combatants.getCombatants());
    Collections.sort(tempCombatants, (Combatant o1, Combatant o2) -> o2.getInitiative() - o1.getInitiative());
    return tempCombatants;
  }

  private boolean initiativesContains(List<Combatant> initiativesCopy, String name) {
    return initiativesCopy.stream().anyMatch((c) -> (c.getName().equals(name)));
  }

  public CombatMap getCombatMap() {
    return combatMap;
  }

  public void setCombatMap(CombatMap combatMap) {
    this.combatMap = combatMap;
    listeners.combatMapUpdated(combatMap);
  }

  public Combatants getCombatants() {
    return combatants;
  }

  public void load(CombatMap combatMap, List<Combatant> shownCombatants, List<Combatant> hiddenCombatants, String currentInitiative) {
    clear();
    setCombatMap(combatMap);
    shownCombatants.stream().forEach((combatant) -> {
      combatants.addVisible(combatant);
      listeners.combatantAdded(combatant);
    });
    hiddenCombatants.stream().forEach((combatant) -> {
      combatants.addHidden(combatant);
    });
    this.initiatives = getInitiativesRaw();
    initiativeJumpTo(currentInitiative);
  }

  void apply(List<Action> actions) {
    Set<Combatant> updatedCombatants = new HashSet<>();
    actions.stream().forEach((action) -> {
      Combatant targetCombatant = combatants.get(action.getTarget());
      if (targetCombatant != null) {
        action.apply(targetCombatant);
        updatedCombatants.add(targetCombatant);
        listeners.actionTaken(action);
      }
    });
    updatedCombatants.stream().forEach((c) -> listeners.combatantUpdated(c));
  }

}
