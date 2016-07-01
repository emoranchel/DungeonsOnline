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
@Table(name = "DATAFEAT")
public class DataFeat implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "NAME")
  private String name;
  @Basic(optional = false)
  @NotNull
  private String category;
  @Basic(optional = false)
  @NotNull
  private String source;
  @Basic(optional = true)
  private String type;
  @Basic(optional = true)
  private String buffText;
  @Basic(optional = true)
  @Column(length = 800)
  private String bonus;
  @Basic(optional = true)
  @Column(length = 800)
  private String details;
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "featName")
  private List<DataFeatPower> powers;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
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

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public List<DataFeatPower> getPowers() {
    return powers;
  }

  public void setPowers(List<DataFeatPower> powers) {
    this.powers = powers;
  }

}
