package net.dungeons.client;

import java.util.List;
import net.dungeons.model.CombatantType;
import net.dungeons.model.Combatant;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;
import net.dungeons.model.StatusEffect;

public class JsonConverter {

  public static JsonObject toJson(Combatant combatant, boolean asView) {
    JsonObjectBuilder obj = Json.createObjectBuilder();
    obj.add("name", combatant.getName());
    obj.add("x", combatant.getX());
    obj.add("y", combatant.getY());
    obj.add("width", combatant.getWidth());
    obj.add("height", combatant.getHeight());
    obj.add("effects", createEffects(combatant.getEffects()));
    if (asView) {
      obj.add("image", "ImageServlet?image=" + combatant.getImage());
      switch (combatant.getType()) {
        case ally:
          addHpBar(obj, combatant);
          addHpData(obj, combatant);
          addStatus(obj, combatant, CombatantType.values());
          break;
        case npc:
          addHpBar(obj, combatant);
          addStatus(obj, combatant, CombatantType.ally, CombatantType.player);
          break;
        case enemy:
          addHpBar(obj, combatant);
          addStatus(obj, combatant, CombatantType.ally, CombatantType.player);
          break;
        case player:
          addHpBar(obj, combatant);
          addHpData(obj, combatant);
          addStatus(obj, combatant, CombatantType.values());
          break;
        default:
          break;
      }
    } else {
      obj.add("image", combatant.getImage());
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
    combatant.setImage(obj.getString("image", ""));
    combatant.setHp(obj.getInt("hp"));
    combatant.setMaxHp(obj.getInt("hpMax"));
    combatant.setHealingSurge(obj.getInt("healingSurge"));
    combatant.setTotalHealingSurge(obj.getInt("totalHealingSurge", 0));
    combatant.setType(CombatantType.valueOf(obj.getString("type")));
    combatant.setInitiative(obj.getInt("initiative", 0));
    JsonArray effectArr = obj.getJsonArray("effects");
    if (effectArr != null) {
      for (JsonValue val : effectArr) {
        combatant.getEffects().add(toEffect((JsonObject) val));
      }
    }
    return combatant;
  }

  private static void addHpData(JsonObjectBuilder obj, Combatant combatant) {
    obj.add("hp", combatant.getHp());
    obj.add("hpMax", combatant.getMaxHp());
    obj.add("healingSurge", combatant.getHealingSurge());
    obj.add("totalHealingSurge", combatant.getTotalHealingSurge());
  }

  private static void addStatus(JsonObjectBuilder obj, Combatant combatant, CombatantType... sources) {
  }

  private static void addHpBar(JsonObjectBuilder obj, Combatant combatant) {
    obj.add("hpp", combatant.getHpp());
    obj.add("hpColor", combatant.getColor());
  }

  public static CombatMap toCombatMap(JsonObject jsonObject) {
    CombatMap combatMap = new CombatMap();
    combatMap.setHeight(jsonObject.getInt("height", CombatMap.DEFAULT_MAP_HEIGHT));
    combatMap.setWidth(jsonObject.getInt("width", CombatMap.DEFAULT_MAP_WIDTH));
    combatMap.setUrl(jsonObject.getString("url", CombatMap.DEFAULT_MAP_IMAGE));
    return combatMap;
  }

  public static JsonValue toJson(Action action) {
    return Json.createObjectBuilder()
            .add("desc", action.toString())
            .build();
  }
  public static JsonValue toJson(CombatMap combatMap) {
    return Json.createObjectBuilder()
            .add("width", combatMap.getWidth())
            .add("height", combatMap.getHeight())
            .add("url", combatMap.getUrl())
            .build();

  }

  private static JsonArrayBuilder createEffects(List<StatusEffect> effects) {
    JsonArrayBuilder arr = Json.createArrayBuilder();
    for (StatusEffect effect : effects) {
      arr.add(Json.createObjectBuilder()
              .add("duration", effect.getDuration())
              .add("effect", effect.getEffect())
              .add("source", effect.getSource())
              .add("target", effect.getTarget())
      );
    }
    return arr;
  }

  private static StatusEffect toEffect(JsonObject jsonObject) {
    StatusEffect effect = new StatusEffect();
    effect.setDuration(jsonObject.getString("duration"));
    effect.setEffect(jsonObject.getString("effect"));
    effect.setSource(jsonObject.getString("source"));
    effect.setTarget(jsonObject.getString("target"));
    return effect;
  }
}
