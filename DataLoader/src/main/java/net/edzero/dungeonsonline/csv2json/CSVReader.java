package net.edzero.dungeonsonline.csv2json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CSVReader {

  private final String filename;
  private final String separator;

  public static void read(String filename, String separator, ConsumerEx<CSVData> action) throws Exception {
    new CSVReader(filename, separator).foreach(action);
  }

  public CSVReader(String filename, String separator) {
    this.filename = filename;
    this.separator = separator;
  }

  public void foreach(ConsumerEx<CSVData> action) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
      String line;
      int count = 0;
      while ((line = reader.readLine()) != null) {
        count++;
        if (line.trim().isEmpty() || line.startsWith("'")) {
          continue;
        }
        try {
          action.accept(new CSVData(line.split(separator)));
        } catch (Exception e) {
          System.err.println("ERROR in line[" + count + "]:" + line);
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public class CSVData {

    private final String[] data;

    public String getString(int index) {
      try {
        return data[index];
      } catch (Exception e) {
      }
      return "";
    }

    public int getInt(int index) {
      try {
        return Integer.parseInt(data[index]);
      } catch (Exception e) {
      }
      return 0;
    }

    public boolean getBoolean(int index) {
      try {
        return Boolean.parseBoolean(data[index]);
      } catch (Exception e) {
      }
      return false;
    }

    private CSVData(String[] data) {
      this.data = data;
    }

    public String[] rawData() {
      return data;
    }

  }
}
