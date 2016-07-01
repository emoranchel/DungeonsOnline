package net.dungeons.model;

import java.util.List;
import net.dungeons.data.CharaItem;
import net.dungeons.data.DataItem;

public class CharacterItem {

  private final String name;
  private final int count;
  private final String slot;
  private final boolean worn;
  private final List<Bonus> bonuses;
  private final List<Detail> details;
  private final int level;

  public CharacterItem(CharaItem charaItem) {
    final DataItem item = charaItem.getItem();
    this.name = item.getName();
    this.count = charaItem.getCnt();
    this.slot = item.getSlot();
    this.worn = charaItem.isWorn();
    this.bonuses = JsonIterator.toList(item.getBonus(), Bonus::new);
    this.details = JsonIterator.toList(item.getDetails(), Detail::new);
    this.level = item.getLvl();
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

}
