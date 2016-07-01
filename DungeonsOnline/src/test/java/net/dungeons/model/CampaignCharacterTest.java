package net.dungeons.model;

import java.util.ArrayList;
import java.util.List;
import net.dungeons.data.Chara;
import net.dungeons.data.CharaFeat;
import net.dungeons.data.DataClass;
import net.dungeons.data.CharaSkill;
import net.dungeons.data.DataFeat;
import org.junit.Test;
import static org.junit.Assert.*;

public class CampaignCharacterTest {

  @Test
  public void shouldCalculateMaximumHpCorrectly() {
    Chara cha = new Chara();
    cha.setLvl(1);
    DataClass cla = new DataClass();
    cla.setInitialhp(10);
    cla.setHplevel(5);
    cha.setCharClass(cla);
    cha.setConstitution(10);
    cha.setSkills(new ArrayList<>());
    cha.setBonuses(new ArrayList<>());
    cha.setItem(new ArrayList<>());
    cha.setPowers(new ArrayList<>());

    assertEquals("Check Lvl=1 con=10 ", 10 + 10, new CampaignCharacter(cha).getHp().getValue());

    cha.setConstitution(19); // +4 hp per level

    assertEquals("Check Lvl=1 con=19", 10 + 19, new CampaignCharacter(cha).getHp().getValue());

    cha.setConstitution(10); // +4 hp per level
    cha.setLvl(2);

    assertEquals("Check Lvl=2 con=10", 10 + 10 + 5 + 0, new CampaignCharacter(cha).getHp().getValue());

    cha.setConstitution(19); // +4 hp per level

    assertEquals("Check Lvl=2 con=19", 10 + 19 + 5 + 4, new CampaignCharacter(cha).getHp().getValue());

    cha.setConstitution(4); // -3 hp per level

    assertEquals("Check Lvl=2 con=10", 10 + 4 + 5 + 0, new CampaignCharacter(cha).getHp().getValue());
  }

  @Test
  public void shouldCalculateBonusesCorrectly() throws Exception {
    Chara cha = new Chara();
    cha.setLvl(5);
    cha.setDexterity(14);
    cha.setStrength(10);
    cha.setConstitution(10);
    cha.setWisdom(10);
    DataClass cla = new DataClass();
    cla.setInitialhp(10);
    cla.setHplevel(5);
    cha.setCharClass(cla);
    cha.setConstitution(10);
    List<CharaSkill> skills = new ArrayList<>();
    cha.setSkills(skills);

    cha.setBonuses(new ArrayList<>());
    cha.setItem(new ArrayList<>());
    cha.setPowers(new ArrayList<>());

    CampaignCharacter character = new CampaignCharacter(cha);

    assertEquals("Acrobatics is the skill", "Acrobatics", character.getSkills().get(0).getName());
    assertEquals("Acrobatics=2", 4, character.getSkills().get(0).getBonus());

    List<CharaFeat> bonuses = new ArrayList<>();

    bonuses.add(createCharaFeat("Nimble", "{\"SKILL:Acrobatics\":\"+2\"}"));
    bonuses.add(createCharaFeat("Wise", "{\"WIS\":\"8\"}"));
    bonuses.add(createCharaFeat("Though", "{\"CON\":\"1\",\"STR\":\"1\"}"));
//    bonuses.add(new CharaBonus(0, "Durable", "", "{\"HP\":\"[LVL*2]\"}"));

    cha.setBonuses(bonuses);

    character = new CampaignCharacter(cha);
    assertEquals("Acrobatics=4", 6, character.getSkill("Acrobatics").getBonus());
    assertEquals("WIS", 18, character.getWisdom().getValue());
    assertEquals("STR", 11, character.getStrength().getValue());
    assertEquals("CON", 11, character.getConstitution().getValue());
//    assertEquals("HP", 4, character.getHp().getValue());
  }

  private CharaFeat createCharaFeat(String name, String bonus) {
    CharaFeat cf = new CharaFeat();
    cf.setFeat(new DataFeat());
    cf.getFeat().setBonus(bonus);
    cf.getFeat().setName(name);
    return cf;
  }

}
