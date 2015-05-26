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
@Table(name = "CHARBONUS")
public class CharaBonus {

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Basic(optional = false)
  @NotNull
  private String name;
  @Basic(optional = true)
  private String chara;
  @Basic(optional = false)
  @NotNull
  private String bonus;
  @Basic(optional = true)
  private String description;

  public CharaBonus() {
  }

  public CharaBonus(int id, String name, String chara, String bonus) {
    this.id = id;
    this.name = name;
    this.chara = chara;
    this.bonus = bonus;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getChara() {
    return chara;
  }

  public void setChara(String chara) {
    this.chara = chara;
  }

  public String getBonus() {
    return bonus;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
}
