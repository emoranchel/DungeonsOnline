package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARCLASS")
@NamedQueries({
  @NamedQuery(name = "CharaClass.findAll", query = "SELECT c FROM CharaClass c")})
public class CharaClass implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "NAME")
  private String name;
  @Basic(optional = false)
  @NotNull
  @Column(name = "HPLEVEL")
  private int hplevel;
  @Basic(optional = false)
  @NotNull
  @Column(name = "INITIALHP")
  private int initialhp;
  @Basic(optional = false)
  @NotNull
  @Column(name = "HEALINGSURGES")
  private int healingsurges;

  public CharaClass() {
  }

  public CharaClass(String name) {
    this.name = name;
  }

  public CharaClass(String name, int hplevel, int initialhp, int healingsurges) {
    this.name = name;
    this.hplevel = hplevel;
    this.initialhp = initialhp;
    this.healingsurges = healingsurges;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHplevel() {
    return hplevel;
  }

  public void setHplevel(int hplevel) {
    this.hplevel = hplevel;
  }

  public int getInitialhp() {
    return initialhp;
  }

  public void setInitialhp(int initialhp) {
    this.initialhp = initialhp;
  }

  public int getHealingsurges() {
    return healingsurges;
  }

  public void setHealingsurges(int healingsurges) {
    this.healingsurges = healingsurges;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CharaClass)) {
      return false;
    }
    CharaClass other = (CharaClass) object;
    if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "net.dungeons.data.CharaClass[ name=" + name + " ]";
  }

}
