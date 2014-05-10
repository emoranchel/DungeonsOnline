package net.dungeons.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Combatants implements Iterable<Combatant> {

  private final List<Combatant> shownCombatants;
  private final List<Combatant> hiddenCombatants;
  private final List<Combatant> all;
  private final Set<String> allIds;

  public Combatants() {
    shownCombatants = new ArrayList<>();
    hiddenCombatants = new ArrayList<>();
    all = new ArrayList<>();
    allIds = new HashSet<>();
  }

  void clear() {
    hiddenCombatants.clear();
    shownCombatants.clear();
    all.clear();
    allIds.clear();
  }

  public Combatant hide(String combatantId) {
    Combatant combatant = get(combatantId);
    if (combatant != null) {
      shownCombatants.remove(combatant);
      hiddenCombatants.add(combatant);
      combatant.setHidden(true);
    }
    return combatant;
  }

  public Combatant show(String combatantId) {
    Combatant combatant = get(combatantId);
    if (combatant != null) {
      shownCombatants.add(combatant);
      hiddenCombatants.remove(combatant);
      combatant.setHidden(false);
    }
    return combatant;
  }

  public Combatant addHidden(Combatant combatant) {
    combatant.setHidden(true);
    return add(combatant);
  }

  public Combatant addVisible(Combatant combatant) {
    combatant.setHidden(false);
    return add(combatant);
  }

  public Combatant add(Combatant combatant) {
    if (!allIds.contains(combatant.getName())) {
      allIds.add(combatant.getName());
      all.add(combatant);
      if (combatant.isHidden()) {
        hiddenCombatants.add(combatant);
      } else {
        shownCombatants.add(combatant);
      }
      combatant.setHidden(false);
      return combatant;
    }
    return null;
  }

  public Combatant remove(String combatantId) {
    allIds.remove(combatantId);
    Predicate<? super Combatant> predicate = (c) -> c.getName().equals(combatantId);
    Combatant combatant = all.stream().filter(predicate).findFirst().orElse(null);
    all.removeIf(predicate);
    shownCombatants.removeIf(predicate);
    hiddenCombatants.removeIf(predicate);
    return combatant;
  }

  public Combatant get(String id) {
    return all.stream().filter((shown) -> shown.getName().equals(id)).findFirst().orElse(null);
  }

  public Combatant getCombatant(String name) {
    return shownCombatants.stream().filter((c) -> c.getName().equals(name)).findFirst().orElse(null);
  }

  public Set<String> getAllIds() {
    return allIds;
  }

  public List<Combatant> getCombatants() {
    return shownCombatants;
  }

  public List<Combatant> getHiddenCombatants() {
    return hiddenCombatants;
  }

  public List<Combatant> getAll() {
    return all;
  }

  @Override
  public Iterator<Combatant> iterator() {
    return shownCombatants.iterator();
  }
}
