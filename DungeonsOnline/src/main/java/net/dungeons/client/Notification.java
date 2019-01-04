package net.dungeons.client;

import net.dungeons.model.Combatant;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
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

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("action", getType().toString())
                .add("object", toJson(this))
                .build();
    }

    private JsonValue toJson(Notification<?> notification) {
        switch (notification.getType()) {
            case combatantAdded:
            case combatantRemoved:
            case combatantUpdated:
                return JsonConverter.toJson((Combatant) notification.getObject(), true);
            case initiativeUpdated:
                return toArray((List<String>) notification.getObject());
            case combatMapUpdated:
                return JsonConverter.toJson((CombatMap) notification.getObject());
            case actionTaken:
                return JsonConverter.toJson((Action) notification.getObject());
        }
        return null;
    }

    private JsonArray toArray(List<String> characters) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (String c : characters) {
            arr.add(c);
        }
        return arr.build();
    }

}
