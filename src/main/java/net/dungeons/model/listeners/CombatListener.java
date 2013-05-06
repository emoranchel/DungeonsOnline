package net.dungeons.model.listeners;

import net.dungeons.model.Combatant;
import java.util.List;
import net.dungeons.model.CombatMap;

public interface CombatListener {

  void initiativeUpdated(List<Combatant> order) throws Exception;

  void combatantUpdated(Combatant combatant) throws Exception;

  void combatantAdded(Combatant combatant) throws Exception;

  void combatantRemoved(Combatant combatant) throws Exception;

  void combatMapUpdated(CombatMap combatMap) throws Exception;
}
