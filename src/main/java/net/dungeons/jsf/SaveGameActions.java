package net.dungeons.jsf;

import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
  private String combatFilename = "/C:/dnd/combat.json";
  private String campaignFilename = "/C:/dnd/campaign.json";

  @PostConstruct
  public void init() {
    try {
      loadCombat();
    } catch (IOException ex) {
      Logger.getLogger(SaveGameActions.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public String loadCombat() throws IOException {
    try (FileInputStream fin = new FileInputStream(combatFilename)) {
      loadCombat(data.getCombat(), fin);
    }
    return null;
  }

  public static void loadCombat(Combat combate, InputStream fin) throws IOException {
    try (JsonReader jsonReader = Json.createReader(fin)) {
      List<Combatant> shownCombatants = new ArrayList<>();
      List<Combatant> hiddenCombatants = new ArrayList<>();
      JsonObject rootObject = jsonReader.readObject();
      CombatMap combatMap = JsonConverter.toCombatMap(rootObject.getJsonObject("combatMap"));
      JsonArray shownCombatantsJson = rootObject.getJsonArray("shownCombatants");
      for (int i = 0; i < shownCombatantsJson.size(); i++) {
        shownCombatants.add(JsonConverter.toCharacter(shownCombatantsJson.getJsonObject(i)));
      }
      JsonArray hiddenCombatantsJson = rootObject.getJsonArray("hiddenCombatants");
      for (int i = 0; i < hiddenCombatantsJson.size(); i++) {
        hiddenCombatants.add(JsonConverter.toCharacter(hiddenCombatantsJson.getJsonObject(i)));
      }
      String currentInitiative = rootObject.getString("currentInitiative", null);
      combate.load(combatMap, shownCombatants, hiddenCombatants, currentInitiative);
    }
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
