package net.dungeons.model;

public abstract class Action {

  protected String source;
  protected String target;

  protected Action(String source, String target) {
    this.source = source;
    this.target = target;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public abstract void apply(Combatant target);

  public static class MoveAction extends Action {

    private final int x;
    private final int y;

    public MoveAction(String source, String target, int x, int y) {
      super(source, target);
      this.x = x;
      this.y = y;
    }

    @Override
    public void apply(Combatant target) {
      target.setX(x);
      target.setY(y);
    }

    @Override
    public String toString() {
      return target + " moves to " + x + "," + y;
    }
  }

  public static class DamageAction extends Action {

    private final int value;

    public DamageAction(String source, String target, int value) {
      super(source, target);
      this.value = value;
    }

    @Override
    public void apply(Combatant target) {
      target.setHp(target.getHp() - value);
    }

    @Override
    public String toString() {
      if (target.equals(source)) {
        return target + " receives " + value + " damage";
      } else {
        return source + " deals " + value + " to " + target;
      }
    }
  }

  public static class HealAction extends Action {

    private final int value;
    private final boolean spendHealingSurge;
    private final boolean ignoreMax;

    public HealAction(String source, String target, int value, boolean spendHealingSurge, boolean ignoreMax) {
      super(source, target);
      this.value = value;
      this.spendHealingSurge = spendHealingSurge;
      this.ignoreMax = ignoreMax;
    }

    @Override
    public void apply(Combatant target) {
      int newHp = target.getHp() + value;
      if (ignoreMax) {
        target.setHp(newHp);
      } else {
        if (newHp > target.getMaxHp()) {
          target.setHp(target.getMaxHp());
        } else {
          target.setHp(newHp);
        }
      }
      if (spendHealingSurge) {
        target.setHealingSurge(target.getHealingSurge() - 1);
      }
    }

    @Override
    public String toString() {
      if (value == 0 && spendHealingSurge) {
        return target + " spends a healing surge.";
      }
      String message = "";
      if (source.equals(target)) {
        message += source + " heals " + target + " ";
      } else {
        message += target + " heals ";
      }
      message += value + "hp";
      if (spendHealingSurge) {
        message += " and spends a healing surge.";
      }
      return message;
    }

  }

  public static class AddEffectAction extends Action {

    private final StatusEffect effect;

    public AddEffectAction(String source, String target, StatusEffect effect) {
      super(source, target);
      this.effect = effect;
    }

    @Override
    public void apply(Combatant target) {
      effect.setSource(source);
      effect.setTarget(this.target);
      target.getEffects().add(effect);
    }

    @Override
    public String toString() {
      return target + " gain:" + effect.getEffect() + "until " + effect.getDuration();
    }
  }

  public static class RemoveEffectAction extends Action {

    private final StatusEffect effect;

    public RemoveEffectAction(String source, String target, StatusEffect effect) {
      super(source, target);
      this.effect = effect;
    }

    @Override
    public void apply(Combatant target) {
      target.getEffects().remove(effect);
    }

    @Override
    public String toString() {
      return target + " stops:" + effect.getEffect();
    }
  }
}
