package net.dungeons.model;

import java.util.List;
import java.util.stream.Collectors;
import net.dungeons.data.Chara;

public class CampaignCharacter {

  private final String name;
  private final String charClass;

  private final int level;

  private final CharacterStat hp;
  private final CharacterStat healingSurges;
  private final CharacterStat initiative;
  private final CharacterStat fortitude;
  private final CharacterStat reflexes;
  private final CharacterStat willpower;
  private final CharacterStat armorClass;
  private final CharacterStat speed;

  private final AbilityScore strength;
  private final AbilityScore constitution;
  private final AbilityScore dexterity;
  private final AbilityScore intelligence;
  private final AbilityScore wisdom;
  private final AbilityScore charisma;

  private final CharacterStat strAttack;
  private final CharacterStat conAttack;
  private final CharacterStat dexAttack;
  private final CharacterStat intAttack;
  private final CharacterStat wisAttack;
  private final CharacterStat chaAttack;

  private final List<CharacterSkill> skills;

  public CampaignCharacter(Chara cha) {
    this.name = cha.getName();
    this.charClass = cha.getCharClass().getName();
    this.level = cha.getLvl();

    this.strength = new AbilityScore("STR", cha.getStrength());
    this.constitution = new AbilityScore("CON", cha.getConstitution());
    this.dexterity = new AbilityScore("DEX", cha.getDexterity());
    this.intelligence = new AbilityScore("INT", cha.getIntelligence());
    this.wisdom = new AbilityScore("WIS", cha.getWisdom());
    this.charisma = new AbilityScore("CHA", cha.getCharisma());

    this.skills = cha.getSkills().stream().map((s) -> new CharacterSkill(s, this)).collect(Collectors.toList());

    this.strAttack = new CharacterStat("STR-Attack", 0, true);
    strAttack.addMod("STR", strength::getBonus);
    strAttack.addMod("Level", this::getBonus);
    this.conAttack = new CharacterStat("CON-Attack", 0, true);
    conAttack.addMod("STR", constitution::getBonus);
    conAttack.addMod("Level", this::getBonus);
    this.dexAttack = new CharacterStat("DEX-Attack", 0, true);
    dexAttack.addMod("STR", dexterity::getBonus);
    dexAttack.addMod("Level", this::getBonus);
    this.intAttack = new CharacterStat("INT-Attack", 0, true);
    intAttack.addMod("STR", intelligence::getBonus);
    intAttack.addMod("Level", this::getBonus);
    this.wisAttack = new CharacterStat("WIS-Attack", 0, true);
    wisAttack.addMod("STR", wisdom::getBonus);
    wisAttack.addMod("Level", this::getBonus);
    this.chaAttack = new CharacterStat("CHA-Attack", 0, true);
    chaAttack.addMod("STR", charisma::getBonus);
    chaAttack.addMod("Level", this::getBonus);

    int hpPerLevel = cha.getCharClass().getHplevel();
    int initialHP = cha.getCharClass().getInitialhp();
    int healingsurges = cha.getCharClass().getHealingsurges();

    this.healingSurges = new CharacterStat("Healing Surges", healingsurges, false);
    this.healingSurges.addMod("Level", this::getBonus);
    this.healingSurges.addMod("Constitution Bonus", () -> (Math.max(0, constitution.getBonus())));

    this.hp = new CharacterStat("HP", initialHP, false);
    this.hp.addMod("Constitution Bonus", () -> constitution.getValue());
    this.hp.addMod("Level", () -> ((level - 1) * (hpPerLevel + (Math.max(0, constitution.getBonus())))));

    this.armorClass = new CharacterStat("AC", 10, false);
    this.armorClass.addMod("DEX", dexterity::getBonus); // add armor restriction

    this.initiative = new CharacterStat("Init", 0, true);
    this.initiative.addMod("DEX", dexterity::getBonus); // add armor restriction

    this.reflexes = new CharacterStat("REF", 10, false);
    this.reflexes.addMod("Level", this::getBonus);
    this.reflexes.addMod("Ability", () -> Math.max(dexterity.getBonus(), intelligence.getBonus()));

    this.fortitude = new CharacterStat("FORT", 10, false);
    this.fortitude.addMod("Level", this::getBonus);
    this.fortitude.addMod("Ability", () -> Math.max(strength.getBonus(), constitution.getBonus()));

    this.willpower = new CharacterStat("WILL", 10, false);
    this.willpower.addMod("Level", this::getBonus);
    this.willpower.addMod("Ability", () -> Math.max(wisdom.getBonus(), charisma.getBonus()));

    this.speed = new CharacterStat("Speed", 6, false);

  }

  public CharacterStat getHealingSurges() {
    return healingSurges;
  }

  public CharacterStat getHp() {
    return hp;
  }

  public AbilityScore getStrength() {
    return strength;
  }

  public AbilityScore getConstitution() {
    return constitution;
  }

  public AbilityScore getDexterity() {
    return dexterity;
  }

  public AbilityScore getIntelligence() {
    return intelligence;
  }

  public AbilityScore getWisdom() {
    return wisdom;
  }

  public AbilityScore getCharisma() {
    return charisma;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public String getCharClass() {
    return charClass;
  }

  public int getBonus() {
    return (level / 2);
  }

  public List<CharacterSkill> getSkills() {
    return skills;
  }

  public CharacterStat getInitiative() {
    return initiative;
  }

  public CharacterStat getFortitude() {
    return fortitude;
  }

  public CharacterStat getReflexes() {
    return reflexes;
  }

  public CharacterStat getWillpower() {
    return willpower;
  }

  public CharacterStat getArmorClass() {
    return armorClass;
  }

  public CharacterStat getSpeed() {
    return speed;
  }

  public CharacterStat getStrAttack() {
    return strAttack;
  }

  public CharacterStat getConAttack() {
    return conAttack;
  }

  public CharacterStat getDexAttack() {
    return dexAttack;
  }

  public CharacterStat getIntAttack() {
    return intAttack;
  }

  public CharacterStat getWisAttack() {
    return wisAttack;
  }

  public CharacterStat getChaAttack() {
    return chaAttack;
  }

}
