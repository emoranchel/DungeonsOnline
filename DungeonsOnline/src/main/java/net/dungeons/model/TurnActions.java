package net.dungeons.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.jsf.SessionData;

@SessionScoped
@Named("turnActions")
public class TurnActions implements Serializable {

  @Inject
  private SessionData sessionData;

  private String source;
  private String target;
  private String effect;
  private String duration;
  private int x;
  private int y;
  private int damage;
  private int heal;
  private boolean healingSurge;
  private boolean ignoreMax;
  private final List<Action> actions = new ArrayList<>();

  public List<Action> getActions() {
    return actions;
  }

  public void moveAction() {
    Arrays.asList(target.split(",")).stream().forEach((target) -> {
      add(new Action.MoveAction(source, target.trim(), x, y));
    });
    reset();
  }

  public void damageAction() {
    Arrays.asList(target.split(",")).stream().forEach((target) -> {
      add(new Action.DamageAction(source, target.trim(), damage));
    });
    reset();
  }

  public void healAction() {
    Arrays.asList(target.split(",")).stream().forEach((target) -> {
      add(new Action.HealAction(source, target.trim(), heal, healingSurge, ignoreMax));
    });
    reset();
  }

  public void addEndTurnAction() {
    add(Action.END_TURN);
  }

  public void addEffectAction() {
    Arrays.asList(target.split(",")).stream().forEach((target) -> {
      StatusEffect status = new StatusEffect();
      status.setDuration(duration);
      status.setEffect(effect);
      add(new Action.AddEffectAction(source, target.trim(), status));
    });
    reset();
  }

  public void removeEffectAction(StatusEffect status) {
    add(new Action.RemoveEffectAction(status.getSource(), status.getTarget(), status));
    reset();
  }

  public void removeAction(Action action) {
    actions.remove(action);
    reset();
  }

  private void add(Action action) {
    if (actions.contains(Action.END_TURN)) {
      actions.remove(Action.END_TURN);
      actions.add(action);
      actions.add(Action.END_TURN);
    } else {
      actions.add(action);
    }
  }

  public void apply() {
    Combat combat = sessionData.getCombat();
    combat.apply(new ArrayList<>(actions));
    clear();
  }

  public void clear() {
    actions.clear();
    reset();
  }

  @PostConstruct
  public void reset() {
    this.source = "";
    if (sessionData.getCombat().getCurrent() != null) {
      this.source = sessionData.getCombat().getCurrent();
    }
    this.target = "";
    this.effect = "";
    this.duration = "";
    this.x = 0;
    this.y = 0;
    this.damage = 0;
    this.heal = 0;
    this.healingSurge = false;
    this.ignoreMax = false;
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

  public String getEffect() {
    return effect;
  }

  public void setEffect(String effect) {
    this.effect = effect;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getHeal() {
    return heal;
  }

  public void setHeal(int heal) {
    this.heal = heal;
  }

  public boolean isHealingSurge() {
    return healingSurge;
  }

  public void setHealingSurge(boolean healingSurge) {
    this.healingSurge = healingSurge;
  }

  public boolean isIgnoreMax() {
    return ignoreMax;
  }

  public void setIgnoreMax(boolean ignoreMax) {
    this.ignoreMax = ignoreMax;
  }

}
