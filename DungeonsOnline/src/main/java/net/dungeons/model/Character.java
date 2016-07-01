package net.dungeons.model;

public class Character {

  private CombatantType defaultType = CombatantType.ally;
  private String name;
  private int hp;
  private int healingSurge;
  private String image;
  private String size;

  public Character() {
    this.name = "char";
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHealingSurge() {
    return healingSurge;
  }

  public void setHealingSurge(int healingSurge) {
    this.healingSurge = healingSurge;
  }


  

  public CombatantType getDefaultType() {
    return defaultType;
  }

  public void setDefaultType(CombatantType defaultType) {
    this.defaultType = defaultType;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Character) {
      return name.equals(((Character) obj).name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
