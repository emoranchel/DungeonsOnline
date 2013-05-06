package net.dungeons;

import java.util.ArrayList;
import java.util.List;

public class Combatant {

  private CombatantType type = CombatantType.ally;
  private int hp;
  private int maxHp;
  private String name;
  private String small;
  private String medium;
  private int x;
  private int y;
  private int healingSurge;
  private int width;
  private int height;
  private int damage;
  private int heal;
  private int initiative;
  private final List<StatusEffect> effects;
  private final List<CombatantAttack> attacks;
  private final CombatantDefenses defenses;

  public Combatant() {
    this.name = "ch";
    this.small = "res/chars/24px/.jpg";
    this.medium = "res/chars/100px/.jpg";
    this.width = 1;
    this.height = 1;
    this.effects = new ArrayList<>();
    this.attacks = new ArrayList<>();
    this.defenses = new CombatantDefenses();
  }

  public void damage() {
    this.hp -= this.damage;
    this.damage = 0;
  }

  public void heal() {
    this.hp += this.heal;
    this.heal = maxHp / 4;
    if (hp > maxHp) {
      this.hp = maxHp;
    }
    healingSurge--;
  }

  public void setMaxHp(int maxHp) {
    if (this.maxHp != maxHp) {
      this.maxHp = maxHp;
      this.heal = this.maxHp / 4;
    }
  }

  public String getColor() {
    if (getHpp() > 75) {
      return "#00FF00";
    } else if (getHpp() > 50) {
      return "#ffe179";
    } else if (getHpp() > 25) {
      return "#f96400";
    } else {
      return "#FF0000";
    }
  }

  public int getHpp() {
    int t = (int) (((double) hp / maxHp) * 100);
    return t > 100 ? 100 : (t < 0 ? 0 : t);
  }

  public CombatantType getType() {
    return type;
  }

  public void setType(CombatantType type) {
    this.type = type;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public String getName() {
    return name;
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

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getHeal() {
    return heal;
  }

  public void setHeal(int heal) {
    this.heal = heal;
  }

  public int getInitiative() {
    return initiative;
  }

  public void setInitiative(int initiative) {
    this.initiative = initiative;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<StatusEffect> getEffects() {
    return effects;
  }

  public List<CombatantAttack> getAttacks() {
    return attacks;
  }

  public CombatantDefenses getDefenses() {
    return defenses;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Combatant) {
      return name.equals(((Combatant) obj).name);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
