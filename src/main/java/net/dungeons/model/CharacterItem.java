package net.dungeons.model;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import net.dungeons.data.CharaItem;

public class CharacterItem {

  private final String name;
  private final String description;
  private final String damage;
  private final int count;
  private final String slot;
  private final boolean worn;
  private final List<Bonus> bonuses;

  public CharacterItem(CharaItem charaItem) {
    this.name = charaItem.getName();
    this.description = charaItem.getDescription();
    this.count = charaItem.getCnt();
    this.slot = charaItem.getSlot();
    this.worn = charaItem.isWorn();
    this.damage = charaItem.getDamage();
    try (JsonReader reader = Json.createReader(new StringReader(charaItem.getBonus()))) {
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

  public int getCount() {
    return count;
  }

  public String getSlot() {
    return slot;
  }

  public boolean isWorn() {
    return worn;
  }

  public List<Bonus> getBonuses() {
    return bonuses;
  }

  public String getDamage() {
    return damage;
  }

}
