package net.dungeons.model;

import java.util.List;
import net.dungeons.data.CharaFeat;

public class CharacterFeat {

  private final String name;
  private final String description;
  private final List<Bonus> bonuses;
  private final List<Detail> details;
  private final String type;
  private final String source;

  public CharacterFeat(CharaFeat feat) {
    this.name = feat.getFeat().getName();
    this.description = feat.getFeat().getBuffText();
    this.bonuses = JsonIterator.toList(feat.getFeat().getBonus(), Bonus::new);
    this.details = JsonIterator.toList(feat.getFeat().getDetails(), Detail::new);
    this.type = feat.getFeat().getType();
    this.source = feat.getFeat().getSource();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
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

}
