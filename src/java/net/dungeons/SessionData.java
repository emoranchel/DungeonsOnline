package net.dungeons;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped
public class SessionData implements Serializable {

  @Inject
  private DataStorage data;
  private Combat combat;

  @PostConstruct
  public void init() {
    combat = data.getCombat();
  }

  public Combat getCombat() {
    return combat;
  }

  public void setCombat(Combat combat) {
    this.combat = combat;
  }
}
