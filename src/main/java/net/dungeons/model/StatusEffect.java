package net.dungeons.model;

public class StatusEffect {

  private CombatantType source;
  private StatusEffectType type;
  private String name;
  private int duration;
  private String effect;

  public StatusEffectType getType() {
    return type;
  }

  public void setType(StatusEffectType type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public CombatantType getSource() {
    return source;
  }

  public void setSource(CombatantType source) {
    this.source = source;
  }
}
