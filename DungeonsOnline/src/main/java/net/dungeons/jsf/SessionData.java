package net.dungeons.jsf;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import net.dungeons.model.Combat;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("sessionData")
public class SessionData implements Serializable {

  private Combat combat;
  @Inject
  private ApplicationData data;

  @PostConstruct
  public void init() {
    combat = data.getCombat();
  }

  public Combat getCombat() {
    return combat;
  }
}
