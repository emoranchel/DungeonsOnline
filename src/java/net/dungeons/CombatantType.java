package net.dungeons;

public enum CombatantType {

  player,
  ally,
  npc,
  enemy,
  master,
  hidden;

  public static CombatantType[] notHidden() {
    return new CombatantType[]{player, ally, npc, enemy, master};
  }

  public boolean isNot(CombatantType... types) {
    for (CombatantType type : types) {
      if (type == this) {
        return false;
      }
    }
    return true;
  }
}
