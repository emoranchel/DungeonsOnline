package net.edzero.dungeonsonline.csv2json;

import java.sql.PreparedStatement;

public interface ColumnInsert {

  public void insert(PreparedStatement statement, CSVReader.CSVData data) throws Exception;
}
