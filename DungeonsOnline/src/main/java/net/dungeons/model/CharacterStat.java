package net.dungeons.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterStat {

  private final String name;
  private final int base;
  private final List<StatMod> mods;
  private final boolean bonus;

  public CharacterStat(String name, int base, boolean bonus) {
    mods = new ArrayList<>();
    this.name = name;
    this.base = base;
    this.bonus = bonus;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    if (bonus) {
      int val = getValue();
      return val == 0 ? "" : val > 0 ? ("+" + val) : Integer.toString(val);
    } else {
      return Integer.toString(getValue());
    }
  }

  public void addMod(String name, StatCalculus mod) {
    mods.add(new StatMod(name, mod));
  }

  public int getValue() {
    return base + mods.stream().mapToInt(StatMod::getValue).sum();
  }

  public List<StatMod> getMods() {
    return mods;
  }

  public int getBase() {
    return base;
  }

}
