package net.dungeons.model;

public class Bonus {

  public static String toString(int bonus) {
    if (bonus > 0) {
      return "+" + bonus;
    }
    if (bonus < 0) {
      return "-" + bonus;
    }
    return "";
  }

}
