package net.dungeons.model;

import net.dungeons.data.CharaPower;

public class CharacterPower {

  private final String source;
  private final String type;
  private final String name;
  private final String description;
  private final String action;
  private final String range;
  private final String target;
  private final String attack;
  private final String effect;
  private final String more;

  public CharacterPower(CharaPower charaPower) {
    this.source = charaPower.getSource();
    this.action = charaPower.getAct();
    this.attack = charaPower.getAttack();
    this.description = charaPower.getDescription();
    this.effect = charaPower.getEffect();
    this.more = charaPower.getMore();
    this.name = charaPower.getName();
    this.range = charaPower.getRange();
    this.target = charaPower.getTarget();
    this.type = charaPower.getType();
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

  public String getDescription() {
    return description;
  }

  public String getAction() {
    return action;
  }

  public String getRange() {
    return range;
  }

  public String getTarget() {
    return target;
  }

  public String getAttack() {
    return attack;
  }

  public String getEffect() {
    return effect;
  }

  public String getMore() {
    return more;
  }

}
