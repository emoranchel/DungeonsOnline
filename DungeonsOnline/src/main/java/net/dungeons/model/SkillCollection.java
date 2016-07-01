package net.dungeons.model;

import java.util.ArrayList;
import java.util.List;

public class SkillCollection {

  static String[][] baseSkills = new String[][]{
    {"DEX", "Acrobatics"},
    {"INT", "Arcana"},
    {"STR", "Athletics"},
    {"CHA", "Bluff"},
    {"CHA", "Diplomacy"},
    {"WIS", "Dungeoneering"},
    {"CON", "Endurance"},
    {"WIS", "Heal"},
    {"INT", "History"},
    {"WIS", "Insight"},
    {"CHA", "Intimidate"},
    {"WIS", "Nature"},
    {"WIS", "Perception"},
    {"INT", "Religion"},
    {"DEX", "Stealth"},
    {"CHA", "Streetwise"},
    {"DEX", "Thievery"}
  };

  static List<CharacterSkill> getBaseSkills(CampaignCharacter chara) {
    List<CharacterSkill> skills = new ArrayList<>();
    for (String[] sk : baseSkills) {
      skills.add(new CharacterSkill(sk[0], sk[1], chara));
    }
    return skills;
  }

}
