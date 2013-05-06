package net.dungeons;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

@ApplicationScoped
@Named("savegame")
public class SaveGame {

  @Inject
  private DataStorage data;
  private String combatFilename = "/D:/dnd/combat.json";
  private String campaignFilename = "/D:/dnd/campaign.json";

  public String loadCombat() throws IOException {
    Combat combat = data.getCombat();
    combat.clear();
    try (FileInputStream fin = new FileInputStream(combatFilename);
            JsonReader jsonReader = Json.createReader(fin)) {
      JsonObject rootObject = jsonReader.readObject();
      JsonArray jsonCharacters = rootObject.getJsonArray("characters");
      for (int i = 0; i < jsonCharacters.size(); i++) {
        combat.addCombatant(JsonConverter.toCharacter(jsonCharacters.getJsonObject(i)));
      }
      combat.initiativeJumpTo(rootObject.getString("currentInitiative", null));
    }
    return null;
  }

  public String saveCombat() {
    Combat combat = data.getCombat();

    try (FileOutputStream fout = new FileOutputStream(combatFilename);
            JsonWriter jsonWriter = Json.createWriter(fout)) {
      JsonArrayBuilder charArray = Json.createArrayBuilder();
      for (Combatant c : combat.getCombatants()) {
        charArray.add(JsonConverter.toJson(c, false));
      }
      JsonObject rootObject = Json.createObjectBuilder()
              .add("characters", charArray)
              .add("currentInitiative", combat.getCurrent())
              .build();
      jsonWriter.writeObject(rootObject);
    } catch (Exception e) {
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
