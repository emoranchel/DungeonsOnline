package net.dungeons;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataStorage {

  private final Campaign campaign;
  private final Combat combat;

  public DataStorage() {
    this.combat = new Combat();
    this.campaign = new Campaign();
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public Combat getCombat() {
    return combat;
  }
}
