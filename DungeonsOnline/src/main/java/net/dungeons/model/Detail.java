package net.dungeons.model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class Detail implements JsonAble {

  private final String name;
  private final String value;

  public Detail(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public JsonObjectBuilder toJson() {
    return Json.createObjectBuilder().add("name", name).add("value", value);
  }

}
