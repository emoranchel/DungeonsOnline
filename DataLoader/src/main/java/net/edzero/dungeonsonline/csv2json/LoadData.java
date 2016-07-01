package net.edzero.dungeonsonline.csv2json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LoadData {

  private final Connection conn;
  private final String prefix;

  public LoadData(Connection conn, String prefix) {
    this.conn = conn;
    this.prefix = prefix;

  }

  private void executeCleanup() throws Exception {
    execute(conn, "DELETE from CHARSKILL");
    execute(conn, "DELETE from CHARPOWER");
    execute(conn, "DELETE from CHARITEM");
    execute(conn, "DELETE from CHARFEAT");
    execute(conn, "DELETE from CHARACTERS");
    execute(conn, "DELETE from DATAPOWER");
    execute(conn, "DELETE from DATAITEMPOWER");
    execute(conn, "DELETE from DATAITEM");
    execute(conn, "DELETE from DATAFEATPOWER");
    execute(conn, "DELETE from DATAFEAT");
    execute(conn, "DELETE from DATACLASS");

  }

  private static void execute(Connection conn, String sql) throws Exception {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    }
  }

  public static void main(String[] args) throws Exception {
    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online")) {
      LoadData loader = new LoadData(conn, "../");
      loader.executeCleanup();

      loader.insertDataClass();
      loader.insertDataFeat();
      loader.insertDataFeatPower();
      loader.insertDataItem();
      loader.insertDataItemPower();
      loader.insertDataPower();
      loader.insertCharacters();
      loader.insertCharFeat();
      loader.insertCharItem();
      loader.insertCharPower();
      loader.insertCharSkill();
    }
  }

  private void insertDataClass() throws Exception {
    SqlInserter.fromFile(prefix + "dataClasses.csv", ";")
            .stringColumn("NAME")
            .intColumn("HealingSurges")
            .intColumn("InitialHP")
            .intColumn("HPLEVEL")
            .insertInto(conn, "DATACLASS");
  }

  private void insertDataFeat() throws Exception {
    SqlInserter.fromFile(prefix + "dataFeats.csv", ";")
            .stringColumn("Category")
            .stringColumn("name")
            .stringColumn("source")
            .stringColumn("type")
            .stringColumn("buffText")
            .stringColumn("bonus")
            .stringColumn("details")
            .insertInto(conn, "DATAFEAT");
  }

  private void insertDataFeatPower() throws Exception {
    SqlInserter.fromFile(prefix + "dataFeatsPower.csv", ";")
            .stringColumn("FEATNAME")
            .stringColumn("SOURCE")
            .stringColumn("TYPE")
            .stringColumn("ACT")
            .stringColumn("RANGE")
            .stringColumn("ATTACK")
            .stringColumn("DAMAGE")
            .stringColumn("EFFECT")
            .stringColumn("MISS")
            .stringColumn("DETAILS")
            .column("NAME", (statement, data) -> {
              statement.setString(11, data.getString(0));
            })
            .insertInto(conn, "DATAFEATPOWER");
  }

  private void insertDataItem() throws Exception {
    SqlInserter.fromFile(prefix + "dataItems.csv", ";")
            .stringColumn("NAME")
            .stringColumn("SLOT")
            .intColumn("LVL")
            .stringColumn("BUFFTEXT")
            .stringColumn("BONUS")
            .stringColumn("WeaponDamage")
            .stringColumn("Details")
            .insertInto(conn, "DATAITEM");
  }

  private void insertDataItemPower() throws Exception {
    SqlInserter.fromFile(prefix + "dataItemsPower.csv", ";")
            .stringColumn("ITEMNAME")
            .stringColumn("SOURCE")
            .stringColumn("TYPE")
            .stringColumn("ACT")
            .stringColumn("RANGE")
            .stringColumn("ATTACK")
            .stringColumn("DAMAGE")
            .stringColumn("EFFECT")
            .stringColumn("MISS")
            .stringColumn("DETAILS")
            .column("NAME", (statement, data) -> {
              statement.setString(11, data.getString(0));
            })
            .insertInto(conn, "DATAITEMPOWER");
  }

  private void insertDataPower() throws Exception {
    SqlInserter.fromFile(prefix + "dataPowers.csv", ";")
            .stringColumn("NAME")
            .stringColumn("SOURCE")
            .stringColumn("TYPE")
            .stringColumn("ACT")
            .stringColumn("RANGE")
            .stringColumn("ATTACK")
            .stringColumn("DAMAGE")
            .stringColumn("EFFECT")
            .stringColumn("MISS")
            .stringColumn("DETAILS")
            .insertInto(conn, "DATAPOWER");
  }

  private void insertCharacters() throws Exception {
    SqlInserter.fromFile(prefix + "characters.csv", ";")
            .stringColumn("NAME")
            .stringColumn("CharClass")
            .intColumn("STRENGTH")
            .intColumn("CONSTITUTION")
            .intColumn("DEXTERITY")
            .intColumn("INTELLIGENCE")
            .intColumn("WISDOM")
            .intColumn("CHARISMA")
            .intColumn("LVL")
            .insertInto(conn, "CHARACTERS");

  }

  private void insertCharFeat() throws Exception {
    SqlInserter.fromFile(prefix + "charFeats.csv", ";")
            .stringColumn("chara")
            .intColumn("lvl")
            .stringColumn("featName")
            .insertInto(conn, "CHARFEAT");
  }

  private void insertCharItem() throws Exception {
    SqlInserter.fromFile(prefix + "charItems.csv", ";")
            .stringColumn("chara")
            .booleanColumn("worn")
            .intColumn("cnt")
            .stringColumn("itemname")
            .insertInto(conn, "CHARITEM");
  }

  private void insertCharPower() throws Exception {
    SqlInserter.fromFile(prefix + "charPowers.csv", ";")
            .stringColumn("chara")
            .intColumn("lvl")
            .stringColumn("powerName")
            .insertInto(conn, "CHARPOWER");
  }

  private void insertCharSkill() throws Exception {
    SqlInserter.fromFile(prefix + "charSkills.csv", ";")
            .stringColumn("chara")
            .stringColumn("ability")
            .stringColumn("skill")
            .insertInto(conn, "CHARSKILL");

  }

}
