package net.dungeons.model;

import net.dungeons.data.BasePower;
import net.dungeons.data.CharaPower;

public class CharacterPower {

  private final String source;

  private final String type;
  private final String name;
  private final String action;
  private final String range;
  private final String attack;

  public CharacterPower(CharaPower power) {
    this(power.getPower().getPower());
  }

  public CharacterPower(BasePower power) {
    this.source = power.getSource();
    this.action = power.getAct();
    this.attack = power.getAttack();
    this.name = power.getName();
    this.range = power.getRange();
    this.type = power.getType();
  }

  public String getSource() {
    return source;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getAction() {
    return action;
  }

  public String getRange() {
    return range;
  }

  public String getAttack() {
    return attack;
  }

}
