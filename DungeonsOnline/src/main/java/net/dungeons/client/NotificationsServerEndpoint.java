package net.dungeons.client;

import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.dungeons.jsf.SessionData;
import net.dungeons.model.Action;
import net.dungeons.model.CombatMap;
import net.dungeons.model.listeners.CombatListener;

@ServerEndpoint(
        value = "/notifications",
        encoders = {NotificationEncoder.class})
@ManagedBean
public class NotificationsServerEndpoint {

  private static final String COMBAT_LISTENER_KEY = "CombatlistenerForCharactersID";
  @Inject
  private SessionData data;

  @OnOpen
  public void onOpen(final Session session) throws IOException, EncodeException {
    Combat combat = data.getCombat();

    send(session, Notification.combatMapUpdated(combat.getCombatMap()));
    for (Combatant combatant : combat.getCombatants()) {
      send(session, Notification.combatantAdded(combatant));
    }
    send(session, Notification.initiativeUpdated(convertInitiatives(combat.getInitiatives())));

    CombatListener combatListener = new CombatListener() {
      @Override
      public void combatantUpdated(Combatant combatant) {
        send(session, Notification.combatantUpdated(combatant));
      }

      @Override
      public void combatantAdded(Combatant combatant) {
        send(session, Notification.combatantAdded(combatant));
      }

      @Override
      public void combatantRemoved(Combatant combatant) {
        send(session, Notification.combatantRemoved(combatant));
      }

      @Override
      public void initiativeUpdated(List<Combatant> order) {
        List<String> initiative = convertInitiatives(order);
        send(session, Notification.initiativeUpdated(initiative));
      }

      @Override
      public void combatMapUpdated(CombatMap combatMap) {
        send(session, Notification.combatMapUpdated(combatMap));
      }

      @Override
      public void actionTaken(Action action) {
        send(session, Notification.actionTaken(action));
      }
    };

    session.getUserProperties().put(COMBAT_LISTENER_KEY, combatListener);
    combat.addListener(combatListener);
  }

  @OnClose
  public void onClose(Session session) throws IOException, EncodeException {
    final Combat combat = data.getCombat();
    CombatListener combatListener = (CombatListener) session.getUserProperties().get(COMBAT_LISTENER_KEY);
    combat.removeListener(combatListener);
  }

  @OnMessage
  public void onMessage(String message) {
  }

  private void send(Session session, Notification notification) {
    try {
      session.getBasicRemote().sendObject(notification);
    } catch (IOException | EncodeException ex) {
      Logger.getLogger(NotificationsServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private List<String> convertInitiatives(List<Combatant> order) {
    return order.stream().map(Combatant::getName).collect(Collectors.toList());
  }
}
