package net.edzero.dungeonsonline.csv2json;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface ConsumerEx<T> {

  public void accept(T t) throws Exception;

  public static <T> void wrap(T t, ConsumerEx<T> sqlConsummer) {
    try {
      sqlConsummer.accept(t);
    } catch (Exception ex) {
      Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
