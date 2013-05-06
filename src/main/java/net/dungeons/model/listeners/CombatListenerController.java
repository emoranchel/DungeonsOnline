package net.dungeons.model.listeners;

import net.dungeons.model.Combatant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.dungeons.model.CombatMap;

public class CombatListenerController {

  private Set<CombatListener> listeners = new HashSet<>();

  public void addListener(CombatListener combatListener) {
    this.listeners.add(combatListener);
  }

  public void removeListener(CombatListener combatListener) {
    this.listeners.remove(combatListener);
  }

  public void sendInitiativeUpdated(List<Combatant> initiative) {
    if(initiative==null){return;}
    Set<CombatListener> listenerCopy = new HashSet<>(listeners);
    for (CombatListener listener : listenerCopy) {
      try {
        listener.initiativeUpdated(initiative);
      } catch (Exception ex) {
      }
    }
  }

  public void sendCombatantUpdated(Combatant c) {
    if(c==null){return;}
    Set<CombatListener> listenersCopy = new HashSet<>(this.listeners);
    for (CombatListener listener : listenersCopy) {
      try {
        listener.combatantUpdated(c);
      } catch (Exception e) {
      }
    }
  }

  public void sendCombatantsUpdated(Iterable<Combatant> combatants) {
    if(combatants==null){return;}
    Set<CombatListener> listenersCopy = new HashSet<>(this.listeners);
    for (CombatListener listener : listenersCopy) {
      for (Combatant c : combatants) {
        try {
          listener.combatantUpdated(c);
        } catch (Exception e) {
        }
      }
    }
  }

  public void sendCombatantAdded(Combatant c) {
    if(c==null){return;}
    Set<CombatListener> listenersCopy = new HashSet<>(this.listeners);
    for (CombatListener listener : listenersCopy) {
      try {
        listener.combatantAdded(c);
      } catch (Exception e) {
      }
    }
  }

  public void sendCombatantRemoved(Combatant c) {
    if(c==null){return;}
    Set<CombatListener> listenersCopy = new HashSet<>(this.listeners);
    for (CombatListener listener : listenersCopy) {
      try {
        listener.combatantRemoved(c);
      } catch (Exception e) {
      }
    }
  }

  public void sendCombatMapUpdated(CombatMap c) {
    if(c==null){return;}
    Set<CombatListener> listenersCopy = new HashSet<>(this.listeners);
    for (CombatListener listener : listenersCopy) {
      try {
        listener.combatMapUpdated(c);
      } catch (Exception e) {
      }
    }
  }
}
