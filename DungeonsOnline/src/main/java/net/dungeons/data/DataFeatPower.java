package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DATAFEATPOWER")
public class DataFeatPower implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Basic(optional = false)
  @NotNull
  private String featName;
  @Embedded
  private BasePower power;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFeatName() {
    return featName;
  }

  public void setFeatName(String featName) {
    this.featName = featName;
  }

  public BasePower getPower() {
    return power;
  }

  public void setPower(BasePower power) {
    this.power = power;
  }

}
