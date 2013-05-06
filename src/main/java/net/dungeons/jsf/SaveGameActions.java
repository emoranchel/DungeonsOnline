package net.dungeons.jsf;

import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import net.dungeons.client.JsonConverter;
import net.dungeons.model.CombatMap;

@ApplicationScoped
@Named("saveGameActions")
public class SaveGameActions {

  @Inject
  private SessionData data;
  private String combatFilename = "/D:/dnd/combat.json";
  private String campaignFilename = "/D:/dnd/campaign.json";

  public String loadCombat() throws IOException {
    Combat combat = data.getCombat();
    combat.clear();
    try (FileInputStream fin = new FileInputStream(combatFilename);
            JsonReader jsonReader = Json.createReader(fin)) {
      JsonObject rootObject = jsonReader.readObject();
      CombatMap combatMap = JsonConverter.toCombatMap(rootObject.getJsonObject("combatMap"));
      combat.setCombatMap(combatMap);
      JsonArray shownCombatants = rootObject.getJsonArray("shownCombatants");
      for (int i = 0; i < shownCombatants.size(); i++) {
        combat.getCombatants().addVisible(JsonConverter.toCharacter(shownCombatants.getJsonObject(i)));
      }
      JsonArray hiddenCombatants = rootObject.getJsonArray("hiddenCombatants");
      for (int i = 0; i < hiddenCombatants.size(); i++) {
        combat.getCombatants().addHidden(JsonConverter.toCharacter(hiddenCombatants.getJsonObject(i)));
      }
      combat.initiativeJumpTo(rootObject.getString("currentInitiative", null));
    }
    return null;
  }

  public String saveCombat() {
    Combat combat = data.getCombat();

    try (FileOutputStream fout = new FileOutputStream(combatFilename);
            JsonWriter jsonWriter = Json.createWriter(fout)) {
      JsonArrayBuilder shownArray = Json.createArrayBuilder();
      for (Combatant c : combat.getCombatants()) {
        shownArray.add(JsonConverter.toJson(c, false));
      }
      JsonArrayBuilder hiddenArray = Json.createArrayBuilder();
      for (Combatant c : combat.getCombatants().getHiddenCombatants()) {
        hiddenArray.add(JsonConverter.toJson(c, false));
      }
      JsonObjectBuilder rootObject = Json.createObjectBuilder();
      rootObject.add("shownCombatants", shownArray);
      rootObject.add("hiddenCombatants", hiddenArray);
      if (combat.getCurrent() != null) {
        rootObject.add("currentInitiative", combat.getCurrent());
      }
      rootObject.add("combatMap", JsonConverter.toJson(combat.getCombatMap()));
      jsonWriter.writeObject(rootObject.build());
    } catch (Exception ex) {
      Logger.getLogger(SaveGameActions.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }

  public String getCombatFilename() {
    return combatFilename;
  }

  public void setCombatFilename(String combatFilename) {
    this.combatFilename = combatFilename;
  }

  public String getCampaignFilename() {
    return campaignFilename;
  }

  public void setCampaignFilename(String campaignFilename) {
    this.campaignFilename = campaignFilename;
  }
}
