package net.edzero.dungeonsonline.csv2json;

import java.sql.ResultSet;

public interface ColumnSelect {

  public String select(ResultSet rs) throws Exception;
}
