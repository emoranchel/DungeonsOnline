package net.dungeons.model;

public enum CombatantType {

  player("#00FF00"),
  ally("#0000FF"),
  npc("#00DDDD"),
  enemy("#FF0000"),
  master("#FFFFFF");
  private String color;

  private CombatantType(String color) {
    this.color = color;
  }

  public boolean isNot(CombatantType... types) {
    for (CombatantType type : types) {
      if (type == this) {
        return false;
      }
    }
    return true;
  }

  public String getColor() {
    return color;
  }
}
