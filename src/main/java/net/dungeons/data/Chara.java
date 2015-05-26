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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARACTERS")
public class Chara implements Serializable {

  private static final long serialVersionUID = 5L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "NAME")
  private String name;
  @Basic(optional = false)
  @NotNull
  @Column(name = "STRENGTH")
  private int strength;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CONSTITUTION")
  private int constitution;
  @Basic(optional = false)
  @NotNull
  @Column(name = "DEXTERITY")
  private int dexterity;
  @Basic(optional = false)
  @NotNull
  @Column(name = "INTELLIGENCE")
  private int intelligence;
  @Basic(optional = false)
  @NotNull
  @Column(name = "WISDOM")
  private int wisdom;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CHARISMA")
  private int charisma;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CHARCLASS")
  private CharaClass charClass;
  @Basic(optional = false)
  @NotNull
  @Column(name = "LVL")
  private int lvl;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "CTYPE")
  private String ctype;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn()
  private List<CharaSkill> skills;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "chara")
  private List<CharaBonus> bonuses;

  public Chara() {
  }

  public Chara(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int getConstitution() {
    return constitution;
  }

  public void setConstitution(int constitution) {
    this.constitution = constitution;
  }

  public int getDexterity() {
    return dexterity;
  }

  public void setDexterity(int dexterity) {
    this.dexterity = dexterity;
  }

  public int getIntelligence() {
    return intelligence;
  }

  public void setIntelligence(int intelligence) {
    this.intelligence = intelligence;
  }

  public int getWisdom() {
    return wisdom;
  }

  public void setWisdom(int wisdom) {
    this.wisdom = wisdom;
  }

  public int getCharisma() {
    return charisma;
  }

  public void setCharisma(int charisma) {
    this.charisma = charisma;
  }

  public CharaClass getCharClass() {
    return charClass;
  }

  public void setCharClass(CharaClass charclass) {
    this.charClass = charclass;
  }

  public int getLvl() {
    return lvl;
  }

  public void setLvl(int lvl) {
    this.lvl = lvl;
  }

  public String getCtype() {
    return ctype;
  }

  public void setCtype(String ctype) {
    this.ctype = ctype;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }

  public List<CharaSkill> getSkills() {
    return skills;
  }

  public void setSkills(List<CharaSkill> skills) {
    this.skills = skills;
  }

  public List<CharaBonus> getBonuses() {
    return bonuses;
  }

  public void setBonuses(List<CharaBonus> bonuses) {
    this.bonuses = bonuses;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Chara)) {
      return false;
    }
    Chara other = (Chara) object;
    if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Chara[" + name + "]";
  }

}
