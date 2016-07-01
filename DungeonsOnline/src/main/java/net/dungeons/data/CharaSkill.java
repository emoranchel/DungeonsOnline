package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARSKILL")
public class CharaSkill implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected CharaSkillPK id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 3)
  @Column(name = "ABILITY")
  private String ability;

  public CharaSkill() {
  }

  public CharaSkill(CharaSkillPK charaSkillPK) {
    this.id = charaSkillPK;
  }

  public CharaSkill(CharaSkillPK charaSkillPK, String ability) {
    this.id = charaSkillPK;
    this.ability = ability;
  }

  public CharaSkill(String chara, String skill) {
    this.id = new CharaSkillPK(chara, skill);
  }

  public CharaSkillPK getId() {
    return id;
  }

  public void setId(CharaSkillPK id) {
    this.id = id;
  }

  public String getChara() {
    return id.getChara();
  }

  public String getSkill() {
    return id.getSkill();
  }

  public String getAbility() {
    return ability;
  }

  public void setAbility(String ability) {
    this.ability = ability;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CharaSkill)) {
      return false;
    }
    CharaSkill other = (CharaSkill) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "net.dungeons.data.CharaSkill[ charaSkillPK=" + id + " ]";
  }

}
