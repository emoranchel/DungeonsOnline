package net.dungeons.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import net.dungeons.data.Chara;

public class CampaignCharacter implements JsonAble {

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

  private final Map<String, CharacterStat> allStats = new HashMap<>();

  private final List<CharacterFeat> features;
  private final List<CharacterSkill> skills;
  private final List<CharacterItem> items;
  private final List<CharacterPower> powers;
  private final List<CharacterBuff> buffs;

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

    this.skills = SkillCollection.getBaseSkills(this);

    this.skills.addAll(cha.getSkills().stream()
            .map((s) -> new CharacterSkill(s, this))
            .collect(Collectors.toList()));

    this.skills.stream().forEach((skill) -> allStats.put("SKILL:" + skill.getName(), skill.getStat()));

    this.features = cha.getBonuses().stream().map(f -> new CharacterFeat(f, this)).collect(Collectors.toList());
    this.items = cha.getItem().stream().map(i -> new CharacterItem(i, this)).collect(Collectors.toList());

    this.powers = new ArrayList<>();
    this.powers.addAll(cha.getPowers().stream().map(p -> new CharacterPower(p, this)).collect(Collectors.toList()));
    this.powers.addAll(this.features.stream().flatMap((o) -> o.getPowers().stream()).collect(Collectors.toList()));
    this.powers.addAll(this.items.stream().filter((i) -> i.isWorn()).flatMap((o) -> o.getPowers().stream()).collect(Collectors.toList()));

    this.buffs = new ArrayList<>();
    this.buffs.addAll(this.features.stream().flatMap(f -> f.getBuffs().stream()).collect(Collectors.toList()));
    this.buffs.addAll(this.items.stream().filter(i -> i.isWorn()).flatMap(i -> i.getBuffs().stream()).collect(Collectors.toList()));

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
    this.hp.addMod("Constitution Bonus", constitution::getValue);
    this.hp.addMod("Level (" + hpPerLevel + "+" + (Math.max(0, constitution.getBonus())) + ")x" + (level - 1),
            () -> ((level - 1) * (hpPerLevel + (Math.max(0, constitution.getBonus())))));
    
    this.armorClass = new CharacterStat("AC", 10, false);
    this.armorClass.addMod("DEX", dexterity::getBonus); // add armor restriction

    this.initiative = new CharacterStat("Initiative", 0, true);
    this.initiative.addMod("Level", this::getBonus);
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

    allStats.put("STR", strength);
    allStats.put("CON", constitution);
    allStats.put("DEX", dexterity);
    allStats.put("INT", intelligence);
    allStats.put("WIS", wisdom);
    allStats.put("CHA", charisma);

    allStats.put("AC", armorClass);
    allStats.put("Speed", speed);
    allStats.put("FORT", fortitude);
    allStats.put("REF", reflexes);
    allStats.put("WILL", willpower);
    
    allStats.put("INIT", initiative);

    features.stream().forEach((feature) -> {
      feature.getBonuses().stream().forEach((b) -> addBonus(b, feature));
    });
    items.stream().filter(CharacterItem::isWorn).forEach((item) -> {
      item.getBonuses().stream().forEach((b) -> allStats.get(b.getStat()).addMod(item.getName(), b.getBonus()));
    });
  }

  private void addBonus(Bonus b, CharacterFeat bonus) {
    if (allStats.containsKey(b.getStat())) {
      allStats.get(b.getStat()).addMod(bonus.getName(), b.getBonus());
    } else {
      switch (b.getStat()) {
        case "HPLevel":
          this.hp.addMod(bonus.getName(), () -> ((level) * (b.getIntValue())));
          break;
        default:
          System.err.println("Error, bonus not found:" + b.getStat());
      }
    }
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

  public List<CharacterFeat> getBonuses() {
    return features;
  }

  public List<CharacterItem> getItems() {
    return items;
  }

  public List<CharacterPower> getPowers() {
    return powers;
  }

  public List<CharacterBuff> getBuffs() {
    return buffs;
  }

  CharacterSkill getSkill(String name) {
    for (CharacterSkill skill : skills) {
      if (name.equals(skill.getName())) {
        return skill;
      }
    }
    return null;
  }

  public JsonObjectBuilder toJson() {
    return Json.createObjectBuilder()
            .add("name", this.name)
            .add("class", this.charClass)
            .add("level", this.level)
            .add("hp", this.hp.getValue())
            .add("healingsurges", this.healingSurges.getValue())
            .add("initiative", this.initiative.getValue())
            .add("fortitude", this.fortitude.getValue())
            .add("reflexes", this.reflexes.getValue())
            .add("will", this.willpower.getValue())
            .add("armorClass", this.armorClass.getValue())
            .add("speed", this.speed.getValue())
            .add("str", this.strength.getBase())
            .add("dex", this.dexterity.getBase())
            .add("con", this.constitution.getBase())
            .add("wis", this.wisdom.getBase())
            .add("int", this.intelligence.getBase())
            .add("cha", this.charisma.getBase())
            .add("strBonus", this.strength.getBonusStr())
            .add("dexBonus", this.dexterity.getBonusStr())
            .add("conBonus", this.constitution.getBonusStr())
            .add("wisBonus", this.wisdom.getBonusStr())
            .add("intBonus", this.intelligence.getBonusStr())
            .add("chaBonus", this.charisma.getBonusStr())
            .add("strAttack", this.strAttack.getValue())
            .add("conAttack", this.conAttack.getValue())
            .add("dexAttack", this.dexAttack.getValue())
            .add("intAttack", this.intAttack.getValue())
            .add("wisAttack", this.wisAttack.getValue())
            .add("chaAttack", this.chaAttack.getValue())
            .add("skills", JsonAble.toJson(skills))
            .add("features", JsonAble.toJson(features))
            .add("items", JsonAble.toJson(items))
            .add("powers", JsonAble.toJson(powers))
            .add("buffs", JsonAble.toJson(buffs));
  }

}
