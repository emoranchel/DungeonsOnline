package net.dungeons.model;

public class AbilityScore extends CharacterStat {

  public AbilityScore(String name, int base) {
    super(name, base, false);
  }

  public int getBonus() {
    int value = getValue();
    return ((value - (value < 10 ? 11 : 10)) / 2);
  }

  public String getBonusStr() {
    int bonus = getBonus();
    return bonus == 0 ? "0" : bonus > 0 ? ("+" + bonus) : Integer.toString(bonus);
  }
}
