package net.dungeons.model;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

public interface JsonIterator<T> {

  public T item(String name, String value);

  public static void forEach(String json, JsonIterator<Void> iterator) {
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

  public static <T> List<T> toList(String json, JsonIterator<T> iterator) {
    try (JsonReader reader = Json.createReader(new StringReader(json))) {
      JsonObject jsonBonus = reader.readObject();
      return jsonBonus.entrySet()
              .stream()
              .map((entry) -> {
                return iterator.item(entry.getKey(), ((JsonString) entry.getValue()).getString());
              }).collect(Collectors.toList());
    } catch (Exception e) {
      System.err.println("Error in json:" + json);
      System.err.println(e.getClass().getName() + "::" + e.getMessage());
    }
    return Collections.emptyList();
  }
}
