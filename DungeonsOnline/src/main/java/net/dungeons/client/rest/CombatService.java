package net.dungeons.client.rest;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import net.dungeons.client.Notification;
import net.dungeons.jsf.SessionData;
import net.dungeons.model.Combat;
import net.dungeons.model.Combatant;

@Path("combat")
@RequestScoped
public class CombatService {

    @Inject
    private SessionData data;

    @GET
    @Produces("application/json")
    public String getJson() {
        Combat combat = data.getCombat();
        JsonArrayBuilder actions = Json.createArrayBuilder();

        actions.add(Notification.combatMapUpdated(combat.getCombatMap()).toJson());

        for (Combatant combatant : combat.getCombatants()) {
            actions.add(Notification.combatantAdded(combatant).toJson());
        }
        actions.add(Notification.initiativeUpdated(convertInitiatives(combat.getInitiatives())).toJson());
        return actions.build().toString();
    }

    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    private List<String> convertInitiatives(List<Combatant> order) {
        return order.stream().map(Combatant::getName).collect(Collectors.toList());
    }

}
