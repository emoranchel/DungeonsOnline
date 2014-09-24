package net.dungeons.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class UserBean implements Serializable {

  private String userName = "Guest";
  private int id = -1;
  private boolean loggedIn = false;

  public boolean isLoggedIn() {
    return loggedIn;
  }

}
