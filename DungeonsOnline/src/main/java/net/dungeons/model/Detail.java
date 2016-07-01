package net.dungeons.model;

public class Detail {

  private final String name;
  private final String value;

  public Detail(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

}
