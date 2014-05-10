package net.dungeons.client;

import net.dungeons.model.Combatant;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;

public class NotificationEncoder implements Encoder.TextStream<Notification> {

  @Override
  public void encode(Notification notification, Writer writer) throws EncodeException, IOException {
    try (JsonWriter jsonWriter = Json.createWriter(writer)) {
      JsonObject jsonObject = Json.createObjectBuilder()
              .add("action", notification.getType().toString())
              .add("object", toJson(notification))
              .build();
      jsonWriter.writeObject(jsonObject);
    } catch (Exception e) {
      Logger.getLogger(NotificationEncoder.class.getName()).log(Level.WARNING, "ERROR", e);
    }
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

  @Override
  public void init(EndpointConfig config) {
  }

  @Override
  public void destroy() {
  }
}
