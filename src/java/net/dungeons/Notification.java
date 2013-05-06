package net.dungeons;

import java.util.List;

public class Notification<T> {

  public enum Type {

    combatantAdded, combatantUpdated, combatantRemoved, initiativeUpdated;
  }
  private final T object;
  private final Type type;

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
