package net.dungeons;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class JsonConverter {

  public static JsonObject toJson(Combatant combatant, boolean asView) {
    if (combatant.getType() == CombatantType.hidden && asView) {
      return null;
    }
    JsonObjectBuilder obj = Json.createObjectBuilder();
    obj.add("name", combatant.getName());
    obj.add("x", combatant.getX());
    obj.add("y", combatant.getY());
    obj.add("width", combatant.getWidth());
    obj.add("height", combatant.getHeight());
    obj.add("small", combatant.getSmall());
    obj.add("medium", combatant.getMedium());
    if (asView) {
      obj.add("hpp", combatant.getHpp());
      obj.add("hpColor", combatant.getColor());
      switch (combatant.getType()) {
        case ally:
          addHpData(obj, combatant);
          addStatus(obj, combatant, CombatantType.notHidden());
          break;
        case npc:
          addStatus(obj, combatant, CombatantType.ally, CombatantType.player);
          break;
        case enemy:
          addStatus(obj, combatant, CombatantType.ally, CombatantType.player);
          break;
        case master:
          addStatus(obj, combatant, CombatantType.player);
          break;
        case player:
          addHpData(obj, combatant);
          addStatus(obj, combatant, CombatantType.notHidden());
          break;
        default:
          break;
      }
    } else {
      addHpData(obj, combatant);
      addStatus(obj, combatant, CombatantType.values());
      obj.add("type", combatant.getType().name());
      obj.add("initiative", combatant.getInitiative());
    }
    return obj.build();
  }

  public static Combatant toCharacter(JsonObject obj) {
    Combatant combatant = new Combatant();
    combatant.setName(obj.getString("name"));
    combatant.setX(obj.getInt("x"));
    combatant.setY(obj.getInt("y"));
    combatant.setWidth(obj.getInt("width"));
    combatant.setHeight(obj.getInt("height"));
    combatant.setSmall(obj.getString("small"));
    combatant.setMedium(obj.getString("medium"));
    combatant.setHp(obj.getInt("hp"));
    combatant.setMaxHp(obj.getInt("hpMax"));
    combatant.setHealingSurge(obj.getInt("healingSurge"));
    combatant.setType(CombatantType.valueOf(obj.getString("type")));
    combatant.setInitiative(obj.getInt("initiative", 0));
    return combatant;
  }

  private static void addHpData(JsonObjectBuilder obj, Combatant combatant) {
    obj.add("hp", combatant.getHp());
    obj.add("hpMax", combatant.getMaxHp());
    obj.add("healingSurge", combatant.getHealingSurge());
  }

  private static void addStatus(JsonObjectBuilder obj, Combatant combatant, CombatantType... sources) {
  }
}
