package net.dungeons.jsf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Data;

@ApplicationScoped
@Named("applicationData")
public class ApplicationData {

  @Inject
  private Data data;
}
