package net.dungeons.model.listeners;

public interface ListenerCollection<T> {
  void addListener(T t);
  void removeListener(T t);
}
