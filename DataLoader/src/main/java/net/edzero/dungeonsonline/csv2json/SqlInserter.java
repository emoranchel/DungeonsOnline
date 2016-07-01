package net.edzero.dungeonsonline.csv2json;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlInserter {

  private final String filename;
  private final List<Column> columns;
  private final String separator;

  public SqlInserter(String filename, String separator) {
    this.filename = filename;
    this.separator = separator;
    this.columns = new ArrayList<>();
  }

  public static SqlInserter fromFile(String filename, String separator) {
    return new SqlInserter(filename, separator);
  }

  public SqlInserter stringColumn(String columnName) {
    final int index = columns.size();
    column(columnName, (statement, data) -> {
      statement.setString(index + 1, data.getString(index));
    });
    return this;
  }

  public SqlInserter intColumn(String columnName) {
    final int index = columns.size();
    column(columnName, (statement, data) -> {
      statement.setInt(index + 1, data.getInt(index));
    });
    return this;
  }

  public SqlInserter booleanColumn(String columnName) {
    final int index = columns.size();
    column(columnName, (statement, data) -> {
      statement.setBoolean(index + 1, data.getBoolean(index));
    });
    return this;
  }

  public SqlInserter column(String columnName, ColumnStatement stmt) {
    columns.add(new Column(columnName, stmt));
    return this;
  }

  public void insertInto(Connection conn, String tableName) throws Exception {
    String sql = "INSERT INTO " + tableName + "(";
    sql += columns.stream().map(Column::getName).collect(Collectors.joining(", ", "", ""));
    sql += ") values(";
    sql += columns.stream().map((x) -> "?").collect(Collectors.joining(", ", "", ""));
    sql += ")";

    CSVReader reader = new CSVReader(filename, separator);

    try (PreparedStatement insert = conn.prepareStatement(sql)) {
      reader.foreach((data) -> {
        for (Column column : columns) {
          column.getConverter().convert(insert, data);
        }
        insert.executeUpdate();
      });
    }
  }

}
