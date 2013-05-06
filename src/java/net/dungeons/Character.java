package net.dungeons;

public class Character {

  private CombatantType defaultType = CombatantType.ally;
  private String name;
  private int hp;
  private int healingSurge;
  private String small;
  private String medium;
  private int x;
  private int y;
  private int width;
  private int height;

  public Character() {
    this.name = "char";
    this.small = "res/chars/24px/.jpg";
    this.medium = "res/chars/100px/.jpg";
    this.width = 1;
    this.height = 1;
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

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getHealingSurge() {
    return healingSurge;
  }

  public void setHealingSurge(int healingSurge) {
    this.healingSurge = healingSurge;
  }

  public String getSmall() {
    return small;
  }

  public void setSmall(String small) {
    this.small = small;
  }

  public String getMedium() {
    return medium;
  }

  public void setMedium(String medium) {
    this.medium = medium;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
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
