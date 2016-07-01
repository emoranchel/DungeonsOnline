package net.dungeons.client;

import net.dungeons.model.Combatant;
import java.util.List;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;

public class Notification<T> {

  public enum Type {

    combatantAdded, combatantUpdated, combatantRemoved, initiativeUpdated, combatMapUpdated, actionTaken;
  }
  private final T object;
  private final Type type;

  public static Notification<CombatMap> combatMapUpdated(CombatMap combatMap) {
    return new Notification<>(Type.combatMapUpdated, combatMap);
  }

  public static Notification<Combatant> combatantAdded(Combatant combatant) {
    return new Notification<>(Type.combatantAdded, combatant);
  }

  public static Notification<Combatant> combatantRemoved(Combatant combatant) {
    return new Notification<>(Type.combatantRemoved, combatant);
  }

  public static Notification<Combatant> combatantUpdated(Combatant combatant) {
    return new Notification<>(Type.combatantUpdated, combatant);
  }

  public static Notification<List<String>> initiativeUpdated(List<String> intiatives) {
    return new Notification<>(Type.initiativeUpdated, intiatives);
  }

  public static Notification<Action> actionTaken(Action action) {
    return new Notification<>(Type.actionTaken, action);
  }

  private Notification(Type type, T object) {
    this.type = type;
    this.object = object;
  }

  public T getObject() {
    return object;
  }

  public Type getType() {
    return type;
  }
}
