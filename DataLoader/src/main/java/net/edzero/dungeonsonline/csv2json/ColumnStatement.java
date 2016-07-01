package net.edzero.dungeonsonline.csv2json;

import java.sql.PreparedStatement;

public interface ColumnStatement {

  public void convert(PreparedStatement statement, CSVReader.CSVData data) throws Exception;
}
