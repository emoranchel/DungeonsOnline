package net.dungeons.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import net.dungeons.data.CharaItem;
import net.dungeons.data.DataItem;

public class CharacterItem implements JsonAble {

  private final String name;
  private final int count;
  private final String slot;
  private final boolean worn;
  private final List<Bonus> bonuses;
  private final List<Detail> details;
  private final List<CharacterPower> powers;
  private final List<CharacterBuff> buffs;

  private final int level;
  private final String damage;

  public CharacterItem(CharaItem charaItem, CampaignCharacter character) {
    final DataItem item = charaItem.getItem();
    this.name = item.getName();
    this.count = charaItem.getCnt();
    this.slot = item.getSlot();
    this.worn = charaItem.isWorn();
    this.bonuses = JsonIterator.objectToList(item.getBonus(), Bonus::new);
    this.details = JsonIterator.objectToList(item.getDetails(), Detail::new);
    this.powers = charaItem.getItem().getPowers().stream().map(i -> new CharacterPower(i, character)).collect(Collectors.toList());
    this.buffs = CharacterBuff.get(charaItem.getItem().getBuffText(), "I: " + item.getName());
    this.level = item.getLvl();
    this.damage = item.getWeaponDamage();
  }

  public String getName() {
    return name;
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

  public List<Detail> getDetails() {
    return details;
  }

  public int getLevel() {
    return level;
  }

  public List<CharacterPower> getPowers() {
    return powers;
  }

  public List<CharacterBuff> getBuffs() {
    return buffs;
  }

  public String getDamage() {
    return damage;
  }

  @Override
  public JsonObjectBuilder toJson() {
    return Json.createObjectBuilder()
            .add("name", name)
            .add("slot", slot)
            .add("worn", worn)
            .add("count", count)
            .add("damage", damage);
  }

}
