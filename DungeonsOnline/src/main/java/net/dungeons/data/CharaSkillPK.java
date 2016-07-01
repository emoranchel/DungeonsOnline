package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class CharaSkillPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "CHARA")
  private String chara;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "SKILL")
  private String skill;

  public CharaSkillPK() {
  }

  public CharaSkillPK(String chara, String skill) {
    this.chara = chara;
    this.skill = skill;
  }

  public String getChara() {
    return chara;
  }

  public void setChara(String chara) {
    this.chara = chara;
  }

  public String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (chara != null ? chara.hashCode() : 0);
    hash += (skill != null ? skill.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CharaSkillPK)) {
      return false;
    }
    CharaSkillPK other = (CharaSkillPK) object;
    if ((this.chara == null && other.chara != null) || (this.chara != null && !this.chara.equals(other.chara))) {
      return false;
    }
    if ((this.skill == null && other.skill != null) || (this.skill != null && !this.skill.equals(other.skill))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "net.dungeons.data.CharaSkillPK[ chara=" + chara + ", skill=" + skill + " ]";
  }

}
