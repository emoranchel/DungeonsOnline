package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class BasePower implements Serializable {

  @Basic(optional = false)
  @NotNull
  private String name;
  @Basic(optional = false)
  @NotNull
  private String source;
  @Basic(optional = false)
  @NotNull
  private String type;
  @Basic(optional = false)
  @NotNull
  private String act;
  @Basic(optional = true)
  private String range;
  @Basic(optional = true)
  private String attack;
  @Basic(optional = true)
  private String damage;
  @Basic(optional = true)
  private String effect;
  @Basic(optional = true)
  private String miss;
  @Basic(optional = true)
  @Column(length = 800)
  private String details;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getAct() {
    return act;
  }

  public void setAct(String act) {
    this.act = act;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public String getAttack() {
    return attack;
  }

  public void setAttack(String attack) {
    this.attack = attack;
  }

  public String getDamage() {
    return damage;
  }

  public void setDamage(String damage) {
    this.damage = damage;
  }

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public String getMiss() {
    return miss;
  }

  public void setMiss(String miss) {
    this.miss = miss;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

}
