package net.edzero.dungeonsonline.csv2json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadData implements AutoCloseable {

  private final Connection conn;

  public LoadData() throws SQLException {
    this.conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online");
  }

  @Override
  public void close() {
    try {
      this.conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void insertCharacters() throws Exception {
    Map<String, CharClass> classes = new HashMap<>();
    Map<String, Chara> chars = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("chars.csv")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        CharClass cclass = new CharClass();
        cclass.setName(data[1]);
        cclass.setHs(Integer.parseInt(data[2]));
        cclass.setInitialHp(Integer.parseInt(data[3]));
        cclass.setHpLevel(Integer.parseInt(data[4]));
        classes.put(cclass.getName(), cclass);

        Chara chara = new Chara();
        chara.setName(data[0]);
        chara.setCharClass(data[1]);
        chara.setStrenght(Integer.parseInt(data[5]));
        chara.setConstitution(Integer.parseInt(data[6]));
        chara.setDexterity(Integer.parseInt(data[7]));
        chara.setIntelligence(Integer.parseInt(data[8]));
        chara.setWisdom(Integer.parseInt(data[9]));
        chara.setCharisma(Integer.parseInt(data[10]));
        chara.setLevel(Integer.parseInt(data[11]));

        chars.put(chara.getName(), chara);
      }
    }
    try (PreparedStatement insertClass = conn.prepareStatement("Insert into CharClass(name, hplevel, initialhp, healingsurges) values(?,?,?,?)");
            PreparedStatement insertChars = conn.prepareStatement("Insert into Characters(name, Strength,constitution,dexterity,intelligence,wisdom,charisma,charclass,lvl) values(?,?,?,?,?,?,?,?,?)");) {
      classes.values().stream().forEach((charClass) -> {
        try {
          insertClass.setString(1, charClass.getName());
          insertClass.setInt(2, charClass.getHpLevel());
          insertClass.setInt(3, charClass.getInitialHp());
          insertClass.setInt(4, charClass.getHs());
          insertClass.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      });
      chars.values().stream().forEach((chara) -> {
        try {
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
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      });
    }
  }

  public void insertSkills() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("charSkills.csv")));
            PreparedStatement insertSkill = conn.prepareStatement("Insert into CharSkill(chara, skill, ability) values(?,?,?)")) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");
        String name = data[0];
        try {
          insertSkill.setString(1, data[0]);
          insertSkill.setString(2, data[2]);
          insertSkill.setString(3, data[1]);
          insertSkill.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  public void insertItems() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("items.csv")));
            PreparedStatement insertSkill = conn.prepareStatement(
                    "Insert into CharItem(chara, name, description, cnt, damage, slot, worn, bonus) values(?,?,?,?,?,?,?,?)")) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(";");
        try {
          insertSkill.setString(1, data[0]);
          insertSkill.setString(2, data[1]);
          insertSkill.setString(3, data[2]);
          insertSkill.setInt(4, Integer.parseInt(data[3]));
          insertSkill.setString(5, data[4]);
          insertSkill.setString(6, data[5]);
          insertSkill.setBoolean(7, Boolean.parseBoolean(data[6]));
          insertSkill.setString(8, data[7]);
          insertSkill.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  public void insertBonuses() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("bonuses.csv")));
            PreparedStatement insertSkill = conn.prepareStatement("Insert into CharBonus(chara, lvl, name, description, bonus) values(?,?,?,?,?)")) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(";");
        try {
          insertSkill.setString(1, data[0]);
          insertSkill.setInt(2, Integer.parseInt(data[1]));
          insertSkill.setString(3, data[2]);
          insertSkill.setString(4, data[3]);
          insertSkill.setString(5, data[4]);
          insertSkill.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }
  public void insertPowers() throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("powers.csv")));
            PreparedStatement insertSkill = conn.prepareStatement(
                    "Insert into CharPower(chara, source, name, description, type, act, target, range, attack, effect, more ) values(?,?,?,?,?,?,?,?,?,?,?)")) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(";");
        try {
          insertSkill.setString(1, data[0]);
          insertSkill.setString(2, data[1]);
          insertSkill.setString(3, data[2]);
          insertSkill.setString(4, data[3]);
          insertSkill.setString(5, data[4]);
          insertSkill.setString(6, data[5]);
          insertSkill.setString(7, data[6]);
          insertSkill.setString(8, data[7]);
          insertSkill.setString(9, data[8]);
          insertSkill.setString(10, data[9]);
          insertSkill.setString(11, data[10]);
          insertSkill.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  private void executeCleanup() throws Exception {
    execute("DELETE from CHARBONUS");
    execute("DELETE from CHARSKILL");
    execute("DELETE from CHARITEM");
    execute("DELETE from CHARPOWER");
    execute("DELETE from CHARACTERS");
    execute("DELETE from CHARCLASS");
  }

  private void execute(String sql) throws Exception {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    }
  }

  public static void main(String[] args) throws Exception {
    try (LoadData loader = new LoadData()) {
      loader.executeCleanup();
      loader.insertCharacters();
      loader.insertSkills();
      loader.insertBonuses();
      loader.insertItems();
      loader.insertPowers();
    }
  }

}
