package net.dungeons.model.listeners;

import net.dungeons.model.Combatant;
import java.util.List;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;

public interface CombatListener {

  void initiativeUpdated(List<Combatant> order);

  void combatantUpdated(Combatant combatant);

  void combatantAdded(Combatant combatant);

  void combatantRemoved(Combatant combatant);

  void combatMapUpdated(CombatMap combatMap);

  void actionTaken(Action action);
}
