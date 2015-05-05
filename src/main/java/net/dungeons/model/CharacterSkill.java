package net.dungeons.model;

import net.dungeons.data.CharaSkill;

public class CharacterSkill {

  private final String name;
  private final CharacterStat bonus;
  private final String ability;

  public CharacterSkill(CharaSkill skill, CampaignCharacter chara) {
    this.name = skill.getSkill();
    this.bonus = new CharacterStat(skill.getSkill(), skill.getBonus(), true);
    this.ability = skill.getAbility();
    this.bonus.addMod("LEVEL", chara::getBonus);
    switch (skill.getAbility()) {
      case "STR":
        this.bonus.addMod("STR", chara.getStrength()::getBonus);
        break;
      case "DEX":
        this.bonus.addMod("DEX", chara.getDexterity()::getBonus);
        break;
      case "CON":
        this.bonus.addMod("CON", chara.getConstitution()::getBonus);
        break;
      case "INT":
        this.bonus.addMod("INT", chara.getIntelligence()::getBonus);
        break;
      case "WIS":
        this.bonus.addMod("WIS", chara.getWisdom()::getBonus);
        break;
      case "CHA":
        this.bonus.addMod("CHA", chara.getCharisma()::getBonus);
        break;
      default:
    }
  }

  public String getName() {
    return name;
  }

  public String getAbility() {
    return ability;
  }

  public String getBonusStr() {
    return Bonus.toString(getBonus());
  }

  public int getBonus() {
    return bonus.getValue();
  }

  public CharacterStat getStat() {
    return bonus;
  }

}
