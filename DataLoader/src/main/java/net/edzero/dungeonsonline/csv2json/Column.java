package net.edzero.dungeonsonline.csv2json;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Column {

  private final String name;
  private final int index;
  private final ColumnInsert inserter;
  private final ColumnSelect selector;

  public Column(String name, int index, ColumnInsert inserter, ColumnSelect selecter) {
    this.name = name;
    this.index = index;
    this.selector = selecter;
    this.inserter = inserter;
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public void write(PreparedStatement statement, CSVReader.CSVData data) {
    try {
      inserter.insert(statement, data);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public String read(ResultSet rs) {
    try {
      return selector.select(rs);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
