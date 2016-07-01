package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DATAPOWER")
public class DataPower implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "NAME")
  private String powerName;

  @Embedded
  private BasePower power;

  public BasePower getPower() {
    return power;
  }

  public void setPower(BasePower power) {
    this.power = power;
  }

  public String getPowerName() {
    return powerName;
  }

  public void setPowerName(String powerName) {
    this.powerName = powerName;
  }

}
