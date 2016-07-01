package net.dungeons.online.datamanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LoadData {

  private void insertCharacters(Connection conn) throws Exception {
    Map<String, CharClass> classes = new HashMap<>();
    Map<String, Chara> chars = new HashMap<>();
    CSVReader reader = new CSVReader("chars.csv", ";");
    reader.foreach((data) -> {
      CharClass cclass = new CharClass();
      cclass.setName(data.getString(1));
      cclass.setHs(data.getInt(2));
      cclass.setInitialHp(data.getInt(3));
      cclass.setHpLevel(data.getInt(4));
      classes.put(cclass.getName(), cclass);

      Chara chara = new Chara();
      chara.setName(data.getString(0));
      chara.setCharClass(data.getString(1));
      chara.setStrenght(data.getInt(5));
      chara.setConstitution(data.getInt(6));
      chara.setDexterity(data.getInt(7));
      chara.setIntelligence(data.getInt(8));
      chara.setWisdom(data.getInt(9));
      chara.setCharisma(data.getInt(10));
      chara.setLevel(data.getInt(11));

      chars.put(chara.getName(), chara);
    });

    try (PreparedStatement insertClass = conn.prepareStatement("Insert into CharClass(name, hplevel, initialhp, healingsurges) values(?,?,?,?)");
            PreparedStatement insertChars = conn.prepareStatement("Insert into Characters(name, Strength,constitution,dexterity,intelligence,wisdom,charisma,charclass,lvl) values(?,?,?,?,?,?,?,?,?)");) {
      classes.values().stream().forEach((t) -> ConsumerEx.wrap(t, (charClass) -> {
        insertClass.setString(1, charClass.getName());
        insertClass.setInt(2, charClass.getHpLevel());
        insertClass.setInt(3, charClass.getInitialHp());
        insertClass.setInt(4, charClass.getHs());
        insertClass.executeUpdate();
      }));
      chars.values().stream().forEach((t) -> ConsumerEx.wrap(t, (chara) -> {
        insertChars.setString(1, chara.getName());
        insertChars.setInt(2, chara.getStrenght());
        insertChars.setInt(3, chara.getConstitution());
        insertChars.setInt(4, chara.getDexterity());
        insertChars.setInt(5, chara.getIntelligence());
        insertChars.setInt(6, chara.getWisdom());
        insertChars.setInt(7, chara.getCharisma());
        insertChars.setString(8, chara.getCharClass());
        insertChars.setInt(9, chara.getLevel());
        insertChars.executeUpdate();
      }));
    }
  }

  public void insertSkills(Connection conn) throws Exception {
    CSVReader reader = new CSVReader("charSkills.csv", ";");
    try (PreparedStatement insertSkill = conn.prepareStatement("Insert into CharSkill(chara, skill, ability) values(?,?,?)")) {
      reader.foreach((data) -> {
        insertSkill.setString(1, data.getString(0));
        insertSkill.setString(2, data.getString(2));
        insertSkill.setString(3, data.getString(1));
        insertSkill.executeUpdate();
      });
    }
  }

  public void insertItems(Connection conn) throws Exception {
    CSVReader reader = new CSVReader("items.csv", ";");
    try (PreparedStatement insertItems = conn.prepareStatement(
            "Insert into CharItem(chara, name, description, cnt, damage, slot, worn, bonus) values(?,?,?,?,?,?,?,?)")) {
      reader.foreach((data) -> {
        insertItems.setString(1, data.getString(0));
        insertItems.setString(2, data.getString(1));
        insertItems.setString(3, data.getString(2));
        insertItems.setInt(4, data.getInt(3));
        insertItems.setString(5, data.getString(4));
        insertItems.setString(6, data.getString(5));
        insertItems.setBoolean(7, data.getBoolean(6));
        insertItems.setString(8, data.getString(7));
        insertItems.executeUpdate();
      });
    }
  }

  public void insertBonuses(Connection conn) throws Exception {
    CSVReader reader = new CSVReader("bonuses.csv", ";");
    try (PreparedStatement insertSkill = conn.prepareStatement("Insert into CharBonus(chara, lvl, name, description, bonus) values(?,?,?,?,?)")) {
      reader.foreach((data) -> {
        insertSkill.setString(1, data.getString(0));
        insertSkill.setInt(2, data.getInt(1));
        insertSkill.setString(3, data.getString(2));
        insertSkill.setString(4, data.getString(3));
        insertSkill.setString(5, data.getString(4));
        insertSkill.executeUpdate();
      });
    }
  }

  public void insertPowers(Connection conn) throws Exception {
    CSVReader reader = new CSVReader("powers.csv", ";");
    try (PreparedStatement insertPower = conn.prepareStatement(
            "Insert into CharPower(chara, source, name, description, type, act, expr) values(?,?,?,?,?,?,?,?,?,?,?)")) {
      reader.foreach((data) -> {
        insertPower.setString(1, data.getString(0));
        insertPower.setString(2, data.getString(1));
        insertPower.setString(3, data.getString(2));
        insertPower.setString(4, data.getString(3));
        insertPower.setString(5, data.getString(4));
        insertPower.setString(6, data.getString(5));
        insertPower.setString(7, data.getString(6));
        insertPower.setString(8, data.getString(7));
        insertPower.setString(9, data.getString(8));
        insertPower.setString(10, data.getString(9));
        insertPower.executeUpdate();
      });
    }
  }

  private void executeCleanup(Connection conn) throws Exception {
    execute(conn, "DELETE from CHARBONUS");
    execute(conn, "DELETE from CHARSKILL");
    execute(conn, "DELETE from CHARITEM");
    execute(conn, "DELETE from CHARPOWER");
    execute(conn, "DELETE from CHARACTERS");
    execute(conn, "DELETE from CHARCLASS");
  }

  private static void execute(Connection conn, String sql) throws Exception {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    }
  }

  public static void main(String[] args) throws Exception {
    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online")) {
      LoadData loader = new LoadData();
      loader.executeCleanup(conn);
      loader.insertCharacters(conn);
      loader.insertSkills(conn);
      loader.insertBonuses(conn);
      loader.insertItems(conn);
      loader.insertPowers(conn);
    }
  }
}
