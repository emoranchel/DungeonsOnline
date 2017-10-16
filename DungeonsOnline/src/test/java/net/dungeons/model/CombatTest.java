package net.dungeons.model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class CombatTest {

  private Combatant momiji;
  private Combatant kaisoku;
  private Combatant kimo;
  private Combatant evil1;
  private Combatant evil2;
  private Combatant evil3;
  private Combat combat;

  @Before
  public void setup() throws Exception {
    momiji = new TestData("Momiji").initiative(15).build();
    kaisoku = new TestData("Kaisoku").initiative(14).build();
    kimo = new TestData("Kimo").initiative(10).build();
    evil1 = new TestData("Evil1").initiative(7).build();
    evil2 = new TestData("Evil2").initiative(6).build();
    evil3 = new TestData("Evil3").initiative(2).build();
    combat = new Combat();
    combat.addCombatant(momiji, true);
    combat.addCombatant(kaisoku, true);
    combat.addCombatant(kimo, true);
    combat.addCombatant(evil1, true);
    combat.addCombatant(evil2, false);
    combat.addCombatant(evil3, false);
  }

  @Test
  public void testInitiative() throws Exception {
    assertEquals("Momiji", combat.getInitiatives().get(0).getName());
    combat.nextInitiative();
    assertEquals("Kaisoku", combat.getInitiatives().get(0).getName());
    combat.updateCombatants();
    assertEquals("Kaisoku", combat.getInitiatives().get(0).getName());
  }
}