package net.edzero.dungeonsonline.csv2json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkillInserterMomiji {

  public static void main(String[] args) throws Exception {
    List<Skill> skillz = Arrays.asList(new Skill[]{
      new Skill("DEX", "Acrobatics"),
      new Skill("INT", "Arcana"),
      new Skill("STR", "Athletics"),
      new Skill("CHA", "Bluff"),
      new Skill("CHA", "Diplomacy"),
      new Skill("WIS", "Dungeoneering"),
      new Skill("CON", "Endurance"),
      new Skill("WIS", "Heal"),
      new Skill("INT", "History"),
      new Skill("WIS", "Insight"),
      new Skill("CHA", "Intimidate"),
      new Skill("WIS", "Nature"),
      new Skill("WIS", "Perception"),
      new Skill("INT", "Religion"),
      new Skill("DEX", "Stealth"),
      new Skill("CHA", "Streetwise"),
      new Skill("DEX", "Thievery")
    });
    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online");
            PreparedStatement insertSkill = conn.prepareStatement("Insert into CharSkill(chara, skill, ability) values(?,?,?)")) {
      String name = "Momiji";
      skillz.forEach((skill) -> {
        try {
          insertSkill.setString(1, name);
          insertSkill.setString(2, skill.getName());
          insertSkill.setString(3, skill.getAttr());
          insertSkill.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(SkillInserterMomiji.class.getName()).log(Level.SEVERE, null, ex);
        }

      });

    }
  }
}
