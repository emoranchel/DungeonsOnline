package net.dungeons.model;

import java.util.ArrayList;
import net.dungeons.data.Chara;
import net.dungeons.data.CharaClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CampaignCharacterTest {

  @Test
  public void shouldCalculateMaximumHpCorrectly() {
    Chara cha = new Chara();
    cha.setLvl(1);
    CharaClass cla = new CharaClass();
    cla.setInitialhp(10);
    cla.setHplevel(5);
    cha.setCharClass(cla);
    cha.setConstitution(10);
    cha.setSkills(new ArrayList<>());

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

}
