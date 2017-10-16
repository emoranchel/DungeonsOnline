package net.dungeons.client.rest;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import net.dungeons.data.Chara;
import net.dungeons.model.CampaignCharacter;

@Path("char")
@RequestScoped
public class CharService {

  @PersistenceContext
  private EntityManager em;

  @GET
  @Produces("application/json")
  @Path("{id}")
  public JsonObject get(@PathParam("id") String character) {
    try {
      return new CampaignCharacter(em.find(Chara.class, character)).toJson().build();
    } catch (Exception e) {
      return Json.createObjectBuilder().build();
    }
  }
}
