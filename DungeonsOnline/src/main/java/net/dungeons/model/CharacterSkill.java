package net.dungeons.model;

import net.dungeons.data.CharaSkill;

public class CharacterSkill {

  private final String name;
  private final CharacterStat bonus;
  private final String ability;

  public CharacterSkill(String ability, String name, CampaignCharacter chara) {
    this.name = name;
    this.bonus = new CharacterStat(name, 0, true);
    this.ability = ability;
    this.bonus.addMod("LEVEL", chara::getBonus);
    switch (ability) {
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

  public CharacterSkill(CharaSkill skill, CampaignCharacter chara) {
    this(skill.getAbility(), skill.getSkill(), chara);
  }

  public String getName() {
    return name;
  }

  public String getAbility() {
    return ability;
  }

  public String getBonusStr() {
    return bonus.toString();
  }

  public int getBonus() {
    return bonus.getValue();
  }

  public CharacterStat getStat() {
    return bonus;
  }

}
