package net.dungeons.model;

import java.util.HashMap;
import java.util.Map;

public class Campaign {

  private final Map<String, Character> characters;

  public Campaign() {
    characters = new HashMap<>();
  }
}
