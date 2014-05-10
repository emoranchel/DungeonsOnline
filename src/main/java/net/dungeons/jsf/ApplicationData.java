package net.dungeons.jsf;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.dungeons.model.Combat;

@ApplicationScoped
@Named("applicationData")
public class ApplicationData {

  @Inject
  private ServletContext context;

  private Combat combat = new Combat();

  public Combat getCombat() {
    return combat;
  }

  public String absPath(String path) {
    if (path.charAt(0) == '/') {
      return path(path);
    } else {
      return path("/" + path);
    }
  }

  public String path(String path) {
    if (path.charAt(0) == '/') {
      String contextPath = context.getContextPath();
      if (contextPath.length() == 0 || ("/".equals(contextPath))) {
        return "/" + path;
      }
      if (contextPath.charAt(0) == '/') {
        return contextPath + path;
      } else {
        return "/" + contextPath + path;
      }
    }
    return path;
  }
}
