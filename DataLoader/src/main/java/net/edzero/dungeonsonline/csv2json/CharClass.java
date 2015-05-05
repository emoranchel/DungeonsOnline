package net.edzero.dungeonsonline.csv2json;

public class CharClass {

  private String name;
  private int initialHp;
  private int hpLevel;
  private int hs;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getInitialHp() {
    return initialHp;
  }

  public void setInitialHp(int initialHp) {
    this.initialHp = initialHp;
  }

  public int getHpLevel() {
    return hpLevel;
  }

  public void setHpLevel(int hpLevel) {
    this.hpLevel = hpLevel;
  }

  public int getHs() {
    return hs;
  }

  public void setHs(int hs) {
    this.hs = hs;
  }

}
