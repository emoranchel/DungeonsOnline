package net.dungeons.model;

import java.util.List;
import java.util.stream.Collectors;
import net.dungeons.data.CharaFeat;
import net.dungeons.data.DataFeat;

public class CharacterFeat {

  private final String name;
  private final List<Bonus> bonuses;
  private final List<Detail> details;
  private final List<CharacterPower> powers;
  private final List<CharacterBuff> buffs;
  private final String type;
  private final String source;
  private final int level;
  private final String category;

  public CharacterFeat(CharaFeat charaFeat, CampaignCharacter character) {
    final DataFeat feat = charaFeat.getFeat();
    this.level = charaFeat.getLvl();
    this.name = feat.getName();
    this.bonuses = JsonIterator.objectToList(feat.getBonus(), Bonus::new);
    this.details = JsonIterator.objectToList(feat.getDetails(), Detail::new);
    this.buffs = CharacterBuff.get(feat.getBuffText(), "F: " + feat.getName());
    this.powers = feat.getPowers().stream().map(f->new CharacterPower(f, character)).collect(Collectors.toList());
    this.type = feat.getType();
    this.source = feat.getSource();
    this.category = feat.getCategory();
  }

  public String getName() {
    return name;
  }

  public List<Bonus> getBonuses() {
    return bonuses;
  }

  public List<Detail> getDetails() {
    return details;
  }

  public String getType() {
    return type;
  }

  public String getSource() {
    return source;
  }

  public List<CharacterPower> getPowers() {
    return powers;
  }

  public List<CharacterBuff> getBuffs() {
    return buffs;
  }

  public int getLevel() {
    return level;
  }

  public String getCategory() {
    return category;
  }

}
