package net.dungeons.client;

import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.dungeons.jsf.SessionData;
import net.dungeons.model.CombatMap;
import net.dungeons.model.listeners.CombatListener;

@ServerEndpoint(
        value = "/notifications",
        encoders = {NotificationEncoder.class})
@RequestScoped
public class NotificationsServerEndpoint {

  private static final String COMBAT_LISTENER_KEY = "CombatlistenerForCharactersID";
  @Inject
  private SessionData data;

  @OnOpen
  public void onOpen(final Session session) throws IOException, EncodeException {
    Combat combat = data.getCombat();

    session.getBasicRemote().sendObject(Notification.combatMapUpdated(combat.getCombatMap()));
    for (Combatant combatant : combat.getCombatants()) {
      session.getBasicRemote().sendObject(Notification.combatantAdded(combatant));
    }
    session.getBasicRemote().sendObject(Notification.initiativeUpdated(convertInitiatives(combat.getInitiatives())));

    CombatListener combatListener = new CombatListener() {
      @Override
      public void combatantUpdated(Combatant combatant) throws Exception {
        session.getBasicRemote().sendObject(Notification.combatantUpdated(combatant));
      }

      @Override
      public void combatantAdded(Combatant combatant) throws Exception {
        session.getBasicRemote().sendObject(Notification.combatantAdded(combatant));
      }

      @Override
      public void combatantRemoved(Combatant combatant) throws Exception {
        session.getBasicRemote().sendObject(Notification.combatantRemoved(combatant));
      }

      @Override
      public void initiativeUpdated(List<Combatant> order) throws Exception {
        List<String> initiative = convertInitiatives(order);
        session.getBasicRemote().sendObject(Notification.initiativeUpdated(initiative));
      }

      @Override
      public void combatMapUpdated(CombatMap combatMap) throws Exception {
        session.getBasicRemote().sendObject(Notification.combatMapUpdated(combatMap));
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

  private List<String> convertInitiatives(List<Combatant> order) {
    List<String> initiative = new ArrayList<>();
    for (Combatant combatant : order) {
      initiative.add(combatant.getName());
    }
    return initiative;
  }
}
