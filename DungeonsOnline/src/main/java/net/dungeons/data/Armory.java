package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ARMORY")
public class Armory implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "ITEMNAME")
  private DataItem item;

  @Basic(optional = true)
  private int cnt = 1; //1->N = item count, 0 -> Item depleted, -1 Item cant be counted.

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public DataItem getItem() {
    return item;
  }

  public void setItem(DataItem item) {
    this.item = item;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }

}
