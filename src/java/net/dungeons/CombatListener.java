package net.dungeons;

import java.util.List;

public interface CombatListener {

  void initiativeUpdated(List<String> order) throws Exception;

  void combatantUpdated(Combatant combatant) throws Exception;

  void combatantAdded(Combatant combatant) throws Exception;

  void combatantRemoved(Combatant combatant) throws Exception;
}
