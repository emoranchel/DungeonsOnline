package net.edzero.dungeonsonline.csv2json;

public class Column {

  private final String name;
  private final ColumnStatement converter;

  public Column(String name, ColumnStatement converter) {
    this.name = name;
    this.converter = converter;
  }

  public String getName() {
    return name;
  }

  public ColumnStatement getConverter() {
    return converter;
  }

}
