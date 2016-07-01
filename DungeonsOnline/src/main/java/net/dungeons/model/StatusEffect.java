package net.dungeons.model;

import java.util.Objects;

public class StatusEffect {

  private String target;
  private String source;
  private String effect;
  private String duration;

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public String getDuration() {
    if (duration == null) {
      return null;
    } else {
      if (source != null && target != null) {
        return duration.replaceAll("\\[caster\\]", source).replaceAll("\\[target\\]", target);
      }
      if (source == null && target != null) {
        return duration.replaceAll("\\[target\\]", target);
      }
      if (target == null && source != null) {
        return duration.replaceAll("\\[caster\\]", source);
      }
      return duration;
    }
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.target);
    hash = 37 * hash + Objects.hashCode(this.source);
    hash = 37 * hash + Objects.hashCode(this.effect);
    hash = 37 * hash + Objects.hashCode(this.duration);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final StatusEffect other = (StatusEffect) obj;
    if (!Objects.equals(this.target, other.target)) {
      return false;
    }
    if (!Objects.equals(this.source, other.source)) {
      return false;
    }
    if (!Objects.equals(this.effect, other.effect)) {
      return false;
    }
    if (!Objects.equals(this.duration, other.duration)) {
      return false;
    }
    return true;
  }

}
