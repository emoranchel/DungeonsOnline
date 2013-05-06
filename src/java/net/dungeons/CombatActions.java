package net.dungeons;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("combatActions")
public class CombatActions {

  private Combat combat;
  private DataModel<Combatant> combatants;
  @Inject
  private DataStorage data;
  @Inject
  private CombatActionParams params;

  @PostConstruct
  public void init() {
    combat = data.getCombat();
    combatants = new ListDataModel<>(combat.getCombatants());
  }

  public String update() {
    combat.updateCombatants();
    return null;
  }

  public String update(Combatant combatant) {
    update(combatant.getName());
    return null;
  }

  public String update(String combatantId) {
    combat.updateCombatant(combatantId);
    return null;
  }

  public String createFromMap() {
    params.getNewCombatant().setX(params.getX());
    params.getNewCombatant().setY(params.getY());
    return create();
  }

  public String create() {
    combat.addCombatant(params.getNewCombatant());
    return null;
  }

  public String remove() {
    return remove(combatants.getRowData().getName());
  }

  public String remove(String selected) {
    combat.removeCombatant(selected);
    return null;
  }

  public String dodamage(Combatant character) {
    character.damage();
    combat.updateCombatant(character.getName());
    return null;
  }

  public String doheal(Combatant character) {
    character.heal();
    combat.updateCombatant(character.getName());
    return null;
  }

  public String moveRight(Combatant character) {
    character.setX(character.getX() + 1);
    combat.updateCombatant(character.getName());
    return null;
  }

  public String moveLeft(Combatant character) {
    character.setX(character.getX() - 1);
    combat.updateCombatant(character.getName());
    return null;
  }

  public String moveUp(Combatant character) {
    character.setY(character.getY() - 1);
    combat.updateCombatant(character.getName());
    return null;
  }

  public String moveDown(Combatant character) {
    character.setY(character.getY() + 1);
    combat.updateCombatant(character.getName());
    return null;
  }

  public String move() {
    for (Combatant c : combatants) {
      if (c.getName().equals(params.getTarget())) {
        c.setX(params.getX());
        c.setY(params.getY());
        combat.updateCombatant(c.getName());
        break;
      }
    }
    return null;
  }

  public String nextInitiative() {
    combat.nextInitiative();
    return null;
  }

  public SelectItem[] getTypeValues() {
    SelectItem[] items = new SelectItem[CombatantType.values().length];
    int i = 0;
    for (CombatantType g : CombatantType.values()) {
      items[i++] = new SelectItem(g, g.name());
    }
    return items;
  }

  public SelectItem[] getCharItems() {
    SelectItem[] items = new SelectItem[combatants.getRowCount()];
    int i = 0;
    for (Combatant g : combatants) {
      items[i++] = new SelectItem(g.getName(), g.getName());
    }
    return items;
  }

  public DataModel<Combatant> getCombatants() {
    return combatants;
  }

  public List<Combatant> getInitiatives() {
    List<Combatant> initiatives = new ArrayList<>();
    for (String id : combat.getInitiatives()) {
      initiatives.add(combat.getCombatant(id));
    }
    return initiatives;
  }
}
