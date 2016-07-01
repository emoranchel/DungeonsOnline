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
@Table(name = "CHARFEAT")
public class CharaFeat implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Basic(optional = false)
  @NotNull
  private int lvl;
  @Basic(optional = false)
  @NotNull
  private String chara;
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "FEATNAME")
  private DataFeat feat;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getChara() {
    return chara;
  }

  public void setChara(String chara) {
    this.chara = chara;
  }

  public DataFeat getFeat() {
    return feat;
  }

  public void setFeat(DataFeat feat) {
    this.feat = feat;
  }

  public int getLvl() {
    return lvl;
  }

  public void setLvl(int lvl) {
    this.lvl = lvl;
  }

}
