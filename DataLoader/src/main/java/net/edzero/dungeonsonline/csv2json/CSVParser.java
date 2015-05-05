package net.edzero.dungeonsonline.csv2json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVParser {

  public static void main(String[] args) throws Exception {
    Map<String, CharClass> classes = new HashMap<>();
    Map<String, Chara> chars = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data.csv")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] data = line.split(",");

        CharClass cclass = new CharClass();
        cclass.setHpLevel(Integer.parseInt(data[4]));
        cclass.setHs(Integer.parseInt(data[2]));
        cclass.setInitialHp(Integer.parseInt(data[3]));
        cclass.setName(data[1]);
        classes.put(cclass.getName(), cclass);

        Chara chara = new Chara();
        chara.setCharisma(Integer.parseInt(data[10]));
        chara.setConstitution(Integer.parseInt(data[6]));
        chara.setDexterity(Integer.parseInt(data[7]));
        chara.setIntelligence(Integer.parseInt(data[8]));
        chara.setName(data[0]);
        chara.setStrenght(Integer.parseInt(data[5]));
        chara.setWisdom(Integer.parseInt(data[9]));
        chara.setCharClass(data[1]);
        chara.setLevel(7);

        chars.put(chara.getName(), chara);
      }
    }
    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online");
            PreparedStatement insertClass = conn.prepareStatement("Insert into CharClass(name, hplevel, initialhp, healingsurges) values(?,?,?,?)");
            PreparedStatement insertChars = conn.prepareStatement("Insert into Characters(name, Strength,constitution,dexterity,intelligence,wisdom,charisma,charclass,level) values(?,?,?,?,?,?,?,?,?)");) {
      classes.values().stream().forEach((charClass) -> {
        try {
          insertClass.setString(1, charClass.getName());
          insertClass.setInt(2, charClass.getHpLevel());
          insertClass.setInt(3, charClass.getInitialHp());
          insertClass.setInt(4, charClass.getHs());
          insertClass.executeUpdate();
        } catch (SQLException ex) {
          Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
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
          Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }
      });
    }

  }

}
