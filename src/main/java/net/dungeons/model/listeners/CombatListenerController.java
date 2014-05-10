package net.dungeons.model.listeners;

import net.dungeons.model.Combatant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;

public class CombatListenerController implements CombatListener, ListenerCollection<CombatListener> {

  private final Set<CombatListener> listeners = new HashSet<>();

  @Override
  public void addListener(CombatListener combatListener) {
    this.listeners.add(combatListener);
  }

  @Override
  public void removeListener(CombatListener combatListener) {
    this.listeners.remove(combatListener);
  }

  @Override
  public void combatantAdded(Combatant c) {
    fire(c, (listener, arg) -> listener.combatantAdded(arg));
  }

  @Override
  public void combatantRemoved(Combatant c) {
    fire(c, (listener, arg) -> listener.combatantRemoved(arg));
  }

  @Override
  public void combatantUpdated(Combatant combatant) {
    fire(combatant, (listener, arg) -> listener.combatantUpdated(arg));
  }

  @Override
  public void combatMapUpdated(CombatMap c) {
    fire(c, (listener, arg) -> listener.combatMapUpdated(arg));
  }

  @Override
  public void initiativeUpdated(List<Combatant> order) {
    fire(order, (listener, arg) -> listener.initiativeUpdated(arg));
  }

  @Override
  public void actionTaken(Action action) {
    fire(action, (listener, arg) -> listener.actionTaken(arg));
  }

  private <T> void fire(T arg, Trigger<T> trigger) {
    if (arg != null) {
      new HashSet<>(listeners).stream().forEach((listener) -> {
        try {
          trigger.fire(listener, arg);
        } catch (Exception ex) {
        }
      });
    }
  }

  private interface Trigger<T> {

    void fire(CombatListener listener, T arg) throws Exception;
  }
}
