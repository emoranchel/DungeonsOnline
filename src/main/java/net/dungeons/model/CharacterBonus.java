package net.dungeons.model;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import net.dungeons.data.CharaBonus;

public class CharacterBonus {

  private final String name;
  private final String description;
  private final List<Bonus> bonuses;

  public CharacterBonus(CharaBonus bonus) {
    this.name = bonus.getName();
    this.description = bonus.getDescription();
    try (JsonReader reader = Json.createReader(new StringReader(bonus.getBonus()))) {
      JsonObject jsonBonus = reader.readObject();
      this.bonuses = jsonBonus.entrySet()
              .stream()
              .map((entry) -> {
                return new Bonus(entry.getKey(), ((JsonString) entry.getValue()).getString());
              }).collect(Collectors.toList());
    }
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Bonus> getBonuses() {
    return bonuses;
  }

}
