package net.dungeons.model;

import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface JsonAble {

  public JsonObjectBuilder toJson();

  public static JsonArrayBuilder toJson(List<? extends JsonAble> items) {
    final JsonArrayBuilder arr = Json.createArrayBuilder();
    items.forEach((i) -> arr.add(i.toJson()));
    return arr;
  }

}
