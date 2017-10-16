package net.dungeons.model;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

public interface JsonIterator<T> {

  public T item(String name, String value);

  public static void objectForEach(String json, JsonIterator<Void> iterator) {
    try (JsonReader reader = Json.createReader(new StringReader(json))) {
      JsonObject jsonBonus = reader.readObject();
      jsonBonus.entrySet()
              .stream()
              .forEach((entry) -> {
                iterator.item(entry.getKey(), ((JsonString) entry.getValue()).getString());
              });
    } catch (Exception e) {
      System.err.println("Error in JsonIterator:" + json);
      System.err.println(e.getClass().getName() + "::" + e.getMessage());
    }
  }

  public static <T> List<T> objectToList(String json, JsonIterator<T> iterator) {
    if (json != null && json.trim().length() > 0) {
      try (JsonReader reader = Json.createReader(new StringReader(json))) {
        JsonObject jsonBonus = reader.readObject();
        return jsonBonus.entrySet()
                .stream()
                .map((entry) -> {
                  String key = entry.getKey();
                  String value = "";
                  switch (entry.getValue().getValueType()) {
                    case ARRAY:
                    case OBJECT:
                      String str = entry.getValue().toString();
                      value = str.substring(1, str.length() - 1);
                      break;
                    case STRING:
                      value = ((JsonString) entry.getValue()).getString();
                      break;
                    case NUMBER:
                      value = Integer.toString(((JsonNumber) entry.getValue()).intValue());
                      break;
                    case TRUE:
                      value = "true";
                      break;
                    case FALSE:
                      value = "false";
                      break;
                    case NULL:
                    default:
                  }
                  return iterator.item(key, value);
                }).collect(Collectors.toList());
      } catch (Exception e) {
        System.err.println("Error in json:" + json);
        System.err.println(e.getClass().getName() + "::" + e.getMessage());
      }
    }
    return Collections.emptyList();
  }
}
