package net.dungeons.model;

public class StatMod {

  private final String name;
  private final StatCalculus calculus;

  public StatMod(String name, StatCalculus calculus) {
    this.name = name;
    this.calculus = calculus;
  }

  public int getValue() {
    return calculus.calculate();
  }

  public String getName() {
    return name;
  }

}
