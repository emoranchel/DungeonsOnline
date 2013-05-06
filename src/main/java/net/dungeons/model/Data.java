package net.dungeons.model;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Data {

  private Combat combat = new Combat();

  public Combat getCombat() {
    return combat;
  }
}
