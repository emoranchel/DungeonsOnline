package net.dungeons.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

public class CharacterBuff {

  public static List<CharacterBuff> get(String json, String source) {
    List<CharacterBuff> buffs = new ArrayList<>();
    if (json != null && json.trim().length() != 0) {
      if (json.startsWith("[")) {
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
          JsonArray arr = reader.readArray();
          return arr.stream().map(Object::toString).map(s -> new CharacterBuff(s, source)).collect(Collectors.toList());
        } catch (Exception e) {
          System.err.println("Error in JsonIterator:" + json);
          System.err.println(e.getClass().getName() + "::" + e.getMessage());
        }
      } else {
        buffs.add(new CharacterBuff(json, source));
      }
    }
    return buffs;
  }
  private final String buff;
  private final String source;

  public CharacterBuff(String buff, String source) {
    this.buff = buff;
    this.source = source;
  }

  public String getBuff() {
    return buff;
  }

  public String getSource() {
    return source;
  }

}
