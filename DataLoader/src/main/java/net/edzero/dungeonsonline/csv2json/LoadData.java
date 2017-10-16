package net.edzero.dungeonsonline.csv2json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadData {

  public static void main(String[] args) throws Exception {
    String path = "../";
    path = "save/20170811/";
    preview(path);
//    database(path);
  }

  private static void preview(String path) throws Exception {
    List<DataSet> dataSets = DataSet.get(path);
    Collections.reverse(dataSets);
    dataSets.forEach(dataSet -> {
      System.out.println("Deleting " + dataSet.getTable());
    });
    Collections.reverse(dataSets);
    dataSets.forEach(dataSet -> {
      System.out.println("Inserting " + dataSet.getTable());
      String sql = "INSERT INTO " + dataSet.getTable() + "(";
      sql += dataSet.getColumns().stream().map(Column::getName).collect(Collectors.joining(", ", "", ""));
      sql += ") values(";
      sql += dataSet.getColumns().stream().map((x) -> "?").collect(Collectors.joining(", ", "", ""));
      sql += ")";

      CSVReader reader = new CSVReader(dataSet.getFilename(), dataSet.getSeparator());

      System.out.println(sql);

      reader.foreach((data) -> {
        System.out.println(
                dataSet.getColumns().stream()
                        .map((column) -> column.getName() + ":" + data.getString(column.getIndex()))
                        .collect(Collectors.joining(", ")));
      });
    });
  }

  private static void database(String path) throws Exception {
    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online")) {
      List<DataSet> dataSets = DataSet.get(path);
      Collections.reverse(dataSets);
      dataSets.forEach(dataSet -> {
        System.out.println("Deleting " + dataSet.getTable());
        try (Statement stmt = conn.createStatement()) {
          stmt.execute("DELETE from " + dataSet.getTable());
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      });
      Collections.reverse(dataSets);
      dataSets.forEach(dataSet -> {
        System.out.println("Inserting " + dataSet.getTable());
        String sql = "INSERT INTO " + dataSet.getTable() + "(";
        sql += dataSet.getColumns().stream().map(Column::getName).collect(Collectors.joining(", ", "", ""));
        sql += ") values(";
        sql += dataSet.getColumns().stream().map((x) -> "?").collect(Collectors.joining(", ", "", ""));
        sql += ")";

        CSVReader reader = new CSVReader(dataSet.getFilename(), dataSet.getSeparator());

        try (PreparedStatement insert = conn.prepareStatement(sql)) {
          reader.foreach((data) -> {
            dataSet.getColumns().forEach((column) -> column.write(insert, data));
            insert.executeUpdate();
          });
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      });
    }
  }
}
