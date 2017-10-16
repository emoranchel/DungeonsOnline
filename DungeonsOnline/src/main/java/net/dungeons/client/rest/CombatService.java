package net.dungeons.client.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Path("combat")
@RequestScoped
public class CombatService {

  @GET
  @Produces("application/json")
  public String getJson() {
    return"[{}]";
  }

  @PUT
  @Consumes("application/json")
  public void putJson(String content) {
  }
}
