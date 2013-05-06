package net.dungeons.jsf;

import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.CombatantType;

@RequestScoped
@Named("catalogsData")
public class CatalogsData {

  @Inject
  private SessionData data;

  public SelectItem[] getTypeValues() {
    SelectItem[] items = new SelectItem[CombatantType.values().length];
    int i = 0;
    for (CombatantType g : CombatantType.values()) {
      items[i++] = new SelectItem(g, g.name());
    }
    return items;
  }

  public SelectItem[] getCharItems() {
    Set<String> combatants = data.getCombat().getCombatants().getAllIds();
    SelectItem[] items = new SelectItem[combatants.size()];
    int i = 0;
    for (String g : combatants) {
      items[i++] = new SelectItem(g, g);
    }
    return items;
  }
}
