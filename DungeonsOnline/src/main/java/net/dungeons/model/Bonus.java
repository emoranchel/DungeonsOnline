package net.dungeons.model;

public class Bonus {

  private final String stat;
  private final String value;

  public Bonus(String stat, String value) {
    this.stat = stat;
    this.value = value;
  }

  public String getStat() {
    return stat;
  }

  public String getValue() {
    return value;
  }

  public StatCalculus getBonus() {
    return () -> getIntValue();
  }

  public int getIntValue() {
    return Integer.parseInt(value);
  }
}
