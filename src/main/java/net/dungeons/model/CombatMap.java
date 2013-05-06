package net.dungeons.model;

public class CombatMap {

  public static int DEFAULT_MAP_WIDTH = 16;
  public static int DEFAULT_MAP_HEIGHT = 20;
  public static String DEFAULT_MAP_IMAGE = "res/maps/16tile12.jpg";
  private int width = DEFAULT_MAP_WIDTH;
  private int height = DEFAULT_MAP_HEIGHT;
  private String url = DEFAULT_MAP_IMAGE;

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
