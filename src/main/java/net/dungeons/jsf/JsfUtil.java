package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

@RequestScoped
@Named("jsfUtil")
public class JsfUtil {
  @Inject
  private ServletContext servletContext;
  public String absPath(String path){
    return servletContext.getContextPath()+path;
  }
}
