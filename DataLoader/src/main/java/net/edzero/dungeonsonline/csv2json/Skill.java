package net.edzero.dungeonsonline.csv2json;

public class Skill {

  private final String attr;
  private final String name;

  public Skill(String attr, String name) {
    this.attr = attr;
    this.name = name;
  }

  public String getAttr() {
    return attr;
  }

  public String getName() {
    return name;
  }

}
