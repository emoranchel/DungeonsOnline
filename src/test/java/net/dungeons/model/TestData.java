package net.dungeons.model;

public class TestData {

  Combatant combatant = new Combatant();

  public TestData(String name) {
    combatant.setName(name);
  }

  public TestData initiative(int initiative) {
    combatant.setInitiative(initiative);
    return this;
  }

  public Combatant build() {
    return combatant;
  }
}
