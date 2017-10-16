package net.edzero.dungeonsonline.csv2json;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

  private static final String SEPARATOR = ";";

  public static List<DataSet> get(String prefix) {
    List<DataSet> dataset = new ArrayList<>();
    dataset.add(new DataSet("DATACLASS", prefix + "dataClasses.csv", SEPARATOR)
            .stringColumn("NAME")
            .intColumn("HealingSurges")
            .intColumn("InitialHP")
            .intColumn("HPLEVEL")
    );
    dataset.add(new DataSet("DATARACE", prefix + "dataRaces.csv", SEPARATOR)
            .stringColumn("NAME")
            .stringColumn("BONUS")
    );
    dataset.add(new DataSet("DATAFEAT", prefix + "dataFeats.csv", SEPARATOR)
            .stringColumn("Category")
            .stringColumn("name")
            .stringColumn("source")
            .stringColumn("type")
            .stringColumn("buffText")
            .stringColumn("bonus")
            .stringColumn("details")
    );
    dataset.add(new DataSet("DATAFEATPOWER", prefix + "dataFeatsPower.csv", SEPARATOR)
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
            .stringColumn("NAME")
    );
    dataset.add(new DataSet("DATAITEM", prefix + "dataItems.csv", SEPARATOR)
            .stringColumn("NAME")
            .stringColumn("SLOT")
            .intColumn("LVL")
            .stringColumn("BUFFTEXT")
            .stringColumn("BONUS")
            .stringColumn("WeaponDamage")
            .stringColumn("Details")
    );
    dataset.add(new DataSet("DATAITEMPOWER", prefix + "dataItemsPower.csv", SEPARATOR)
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
            .stringColumn("NAME")
    );
    dataset.add(new DataSet("DATAPOWER", prefix + "dataPowers.csv", SEPARATOR)
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
    );
    dataset.add(new DataSet("CHARACTERS", prefix + "characters.csv", SEPARATOR)
            .stringColumn("NAME")
            .stringColumn("CHARCLASS")
            .intColumn("STRENGTH")
            .intColumn("CONSTITUTION")
            .intColumn("DEXTERITY")
            .intColumn("INTELLIGENCE")
            .intColumn("WISDOM")
            .intColumn("CHARISMA")
            .intColumn("LVL")
            .stringColumn("CTYPE")
            .stringColumn("CHARRACE")
            .stringColumn("GENDER")
    );
    dataset.add(new DataSet("CHARDETAIL", prefix + "charDetails.csv", SEPARATOR)
            .stringColumn("NAME")
            .stringColumn("CSIZE")
            .stringColumn("AGE")
            .stringColumn("ALIGNMENT")
            .stringColumn("BACKGROUND")
            .stringColumn("DESCRIPTION")
            .stringColumn("FAMILY")
            .stringColumn("HEIGHT")
            .stringColumn("JOURNAL")
            .stringColumn("PERSONALITY")
            .stringColumn("RELATIONS")
            .stringColumn("WEIGHT")
    );
    dataset.add(new DataSet("CHARFEAT", prefix + "charFeats.csv", SEPARATOR)
            .stringColumn("chara")
            .intColumn("lvl")
            .stringColumn("featName")
    );
    dataset.add(new DataSet("CHARITEM", prefix + "charItems.csv", SEPARATOR)
            .stringColumn("chara")
            .booleanColumn("worn")
            .intColumn("cnt")
            .stringColumn("itemname")
    );
    dataset.add(new DataSet("CHARPOWER", prefix + "charPowers.csv", SEPARATOR)
            .stringColumn("chara")
            .intColumn("lvl")
            .stringColumn("powerName"));
    dataset.add(new DataSet("CHARSKILL", prefix + "charSkills.csv", SEPARATOR)
            .stringColumn("chara")
            .stringColumn("ability")
            .stringColumn("skill")
    );
    return dataset;
  }

  private final String filename;
  private final String table;
  private final String separator;
  private final List<Column> columns = new ArrayList<>();

  private DataSet(String table, String filename, String separator) {
    this.table = table;
    this.filename = filename;
    this.separator = separator;
  }

  public String getFilename() {
    return filename;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public String getTable() {
    return table;
  }

  public String getSeparator() {
    return separator;
  }

  public DataSet stringColumn(String columnName) {
    final int index = columns.size();
    column(columnName, index,
            (statement, data) -> {
              statement.setString(index + 1, unescape(data.getString(index)));
            },
            (rs) -> escape(rs.getString(columnName)));
    return this;
  }

  public DataSet intColumn(String columnName) {
    final int index = columns.size();
    column(columnName, index,
            (statement, data) -> {
              statement.setInt(index + 1, data.getInt(index));
            },
            (rs) -> "" + rs.getInt(columnName));
    return this;
  }

  public DataSet booleanColumn(String columnName) {
    final int index = columns.size();
    column(columnName, index,
            (statement, data) -> {
              statement.setBoolean(index + 1, data.getBoolean(index));
            },
            (rs) -> "" + rs.getBoolean(columnName));
    return this;
  }

  public DataSet column(String columnName, int index, ColumnInsert insert, ColumnSelect select) {
    columns.add(new Column(columnName, index, insert, select));
    return this;
  }

  private String escape(String string) {
    return string == null ? "" : string;
  }

  private String unescape(String string) {
    return string == null ? "" : string;
  }

}
