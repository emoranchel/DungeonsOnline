package net.dungeons.model;

import java.util.List;
import java.util.Optional;
import net.dungeons.data.BasePower;
import net.dungeons.data.CharaPower;
import net.dungeons.data.DataFeatPower;
import net.dungeons.data.DataItemPower;

public class CharacterPower {

  private final String source;
  private final String type;
  private final String name;
  private final String action;
  private final String range;
  private final String attack;
  private final List<Detail> details;
  private final String damage;
  private final String effect;
  private final String miss;
  private final CampaignCharacter character;

  public CharacterPower(CharaPower power, CampaignCharacter character) {
    this(power.getPower().getPower(), character);
  }

  public CharacterPower(DataItemPower power, CampaignCharacter character) {
    this(power.getPower(), character);
  }

  public CharacterPower(DataFeatPower power, CampaignCharacter character) {
    this(power.getPower(), character);
  }

  public CharacterPower(BasePower power, CampaignCharacter character) {
    this.character = character;
    this.action = power.getAct();
    this.attack = power.getAttack();
    this.damage = power.getDamage();
    this.details = JsonIterator.objectToList(power.getDetails(), Detail::new);
    this.effect = power.getEffect();
    this.miss = power.getMiss();
    this.name = power.getName();
    this.range = power.getRange();
    this.source = power.getSource();
    this.type = power.getType();
  }

  public String getSource() {
    return source;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getAction() {
    return action;
  }

  public String getRange() {
    return range;
  }

  public String getAttack() {
    return attack;
  }

  public List<Detail> getDetails() {
    return details;
  }

  public String getDamage() {
    return damage;
  }

  public String getEffect() {
    return effect;
  }

  public String getMiss() {
    return miss;
  }

  public String getAttackValue() {
    if (attack == null) {
      return attack;
    }
    String[] components = attack.trim().split(" ");
    if (components.length == 3 && "vs".equals(components[1].toLowerCase())) {
      switch (components[0].toLowerCase()) {
        case "str":
          components[0] = "+" + Integer.toString(character.getStrAttack().getValue());
          break;
        case "dex":
          components[0] = "+" + Integer.toString(character.getDexAttack().getValue());
          break;
        case "con":
          components[0] = "+" + Integer.toString(character.getConAttack().getValue());
          break;
        case "int":
          components[0] = "+" + Integer.toString(character.getIntAttack().getValue());
          break;
        case "wis":
          components[0] = "+" + Integer.toString(character.getWisAttack().getValue());
          break;
        case "cha":
          components[0] = "+" + Integer.toString(character.getChaAttack().getValue());
          break;
      }
      return String.join(" ", components);
    }
    return attack;
  }

  public String getDamageValue() {
    if (damage == null) {
      return damage;
    }
    Optional<CharacterItem> weapon = character.getItems().stream().filter(i -> i.isWorn() && i.getDamage() != null && i.getDamage().length() > 0).findFirst();
    String dmg = damage.toLowerCase();
    if (weapon.isPresent()) {
      if (dmg.contains("1[w]")) {
        dmg = dmg.replace("1[w]", weapon.get().getDamage());
      } else if (dmg.indexOf("[w]") == 0) {
        dmg = dmg.replace("[w]", weapon.get().getDamage());
      } else if (dmg.contains("[w]")) {
        dmg = dmg.replace("[w]", "(" + weapon.get().getDamage() + ")");
      }
    }
    dmg = dmg.replace("str", toVal(character.getStrength().getBonus()));
    dmg = dmg.replace("dex", toVal(character.getDexterity().getBonus()));
    dmg = dmg.replace("con", toVal(character.getConstitution().getBonus()));
    dmg = dmg.replace("wis", toVal(character.getWisdom().getBonus()));
    dmg = dmg.replace("int", toVal(character.getIntelligence().getBonus()));
    dmg = dmg.replace("cha", toVal(character.getCharisma().getBonus()));
    return dmg;
  }

  private CharSequence toVal(int bonus) {
    return "(" + Integer.toString(bonus) + ")";
  }
}
