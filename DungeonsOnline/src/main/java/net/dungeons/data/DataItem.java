package net.dungeons.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DATAITEM")
public class DataItem implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "NAME")
  private String name;
  @Basic(optional = false)
  @NotNull
  private String slot;
  @Basic(optional = false)
  @NotNull
  private int lvl;
  @Basic(optional = true)
  private String buffText;
  @Basic(optional = true)
  @Column(length = 800)
  private String bonus;
  @Basic(optional = true)
  private String weaponDamage;
  @Basic(optional = true)
  @Column(length = 800)
  private String details;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "itemName")
  private List<DataItemPower> powers;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public int getLvl() {
    return lvl;
  }

  public void setLvl(int lvl) {
    this.lvl = lvl;
  }

  public String getBuffText() {
    return buffText;
  }

  public void setBuffText(String buffText) {
    this.buffText = buffText;
  }

  public String getBonus() {
    return bonus;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  public String getWeaponDamage() {
    return weaponDamage;
  }

  public void setWeaponDamage(String weaponDamage) {
    this.weaponDamage = weaponDamage;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public List<DataItemPower> getPowers() {
    return powers;
  }

  public void setPowers(List<DataItemPower> powers) {
    this.powers = powers;
  }

}
