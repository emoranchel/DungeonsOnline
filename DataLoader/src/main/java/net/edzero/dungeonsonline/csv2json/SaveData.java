/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.edzero.dungeonsonline.csv2json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

/**
 *
 * @author ed_ze
 */
public class SaveData {

  public static void main(String[] args) throws Exception {
    Calendar cal = new GregorianCalendar();
    String folder = "save/" + String.format("%04d%02d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    new File(folder).mkdirs();

    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DungeonsOnline", "dungeons", "online")) {
      DataSet.get(folder + "/")
              .forEach((dataSet) -> {
                String sql = "SELECT ";
                sql += dataSet.getColumns().stream().map(Column::getName).collect(Collectors.joining(", ", "", ""));
                sql += " FROM " + dataSet.getTable();
                try (PreparedStatement select = conn.prepareStatement(sql);
                        ResultSet rs = select.executeQuery();
                        PrintWriter writer = new PrintWriter(new FileOutputStream(dataSet.getFilename(), false));) {
                  writer.println("'" + dataSet.getColumns().stream().map(Column::getName).collect(Collectors.joining(dataSet.getSeparator(), "", "")));
                  while (rs.next()) {
                    writer.println(dataSet.getColumns().stream().map(c -> c.read(rs)).collect(Collectors.joining(dataSet.getSeparator(), "", "")));
                  }
                } catch (SQLException | FileNotFoundException ex) {
                  throw new RuntimeException(ex);
                }
              });
    }
  }
}
