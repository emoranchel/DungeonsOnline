package net.dungeons.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CHARPOWER")
public class CharaPower {

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Basic(optional = false)
  @NotNull
  private String source;
  @Basic(optional = false)
  @NotNull
  private String name;
  @Basic(optional = false)
  @NotNull
  private String description;
  @Basic(optional = false)
  @NotNull
  private String type;
  @Basic(optional = false)
  @NotNull
  private String act;
  @Basic(optional = false)
  @NotNull
  private String range;
  @Basic(optional = false)
  @NotNull
  private String attack;
  @Basic(optional = false)
  @NotNull
  private String target;
  @Basic(optional = false)
  @NotNull
  private String effect;
  @Basic(optional = false)
  @NotNull
  private String more;
  @Basic(optional = false)
  @NotNull
  private String chara;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public String getMore() {
    return more;
  }

  public void setMore(String more) {
    this.more = more;
  }

  public String getChara() {
    return chara;
  }

  public void setChara(String chara) {
    this.chara = chara;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

}
