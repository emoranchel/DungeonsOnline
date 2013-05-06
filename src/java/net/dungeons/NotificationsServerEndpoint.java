package net.dungeons;

import java.io.IOException;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/notifications",
        encoders = {NotificationEncoder.class})
@ManagedBean
public class NotificationsServerEndpoint {

  private static final String COMBAT_LISTENER_KEY = "CombatlistenerForCharactersID";
  @Inject
  private DataStorage dataStorage;

  @OnOpen
  public void onOpen(final Session session) throws IOException, EncodeException {
    final Combat combat = dataStorage.getCombat();
    for (Combatant combatant : combat.getCombatants()) {
      session.getBasicRemote().sendObject(Notification.combatantAdded(combatant));
    }
    session.getBasicRemote().sendObject(Notification.initiativeUpdated(combat.getInitiatives()));
    CombatListener combatListener = new CombatListener() {
      @Override
      public void initiativeUpdated(List<String> initiative) throws Exception {
        session.getBasicRemote().sendObject(Notification.initiativeUpdated(initiative));
      }

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
    };
    session.getUserProperties().put(COMBAT_LISTENER_KEY, combatListener);
    combat.addListener(combatListener);
  }

  @OnClose
  public void onClose(Session session) throws IOException, EncodeException {
    final Combat combat = dataStorage.getCombat();
    CombatListener combatListener = (CombatListener) session.getUserProperties().get(COMBAT_LISTENER_KEY);
    combat.removeListener(combatListener);
  }

  @OnMessage
  public void onMessage(String message) {
  }
}
