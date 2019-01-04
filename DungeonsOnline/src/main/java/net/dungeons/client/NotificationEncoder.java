package net.dungeons.client;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class NotificationEncoder implements Encoder.TextStream<Notification> {

    @Override
    public void encode(Notification notification, Writer writer) throws EncodeException, IOException {
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(notification.toJson());
        } catch (Exception e) {
            Logger.getLogger(NotificationEncoder.class.getName()).log(Level.WARNING, "ERROR", e);
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
