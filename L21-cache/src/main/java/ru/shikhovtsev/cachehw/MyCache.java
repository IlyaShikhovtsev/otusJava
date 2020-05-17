package ru.shikhovtsev.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

  List<HwListener<K, V>> listeners = new ArrayList<>();
  Map<K, V> data = new WeakHashMap<>();

  @Override
  public void put(K key, V value) {
    data.put(key, value);
    listeners.forEach(l -> l.notify(key, value, "put"));
  }

  @Override
  public void remove(K key) {
    V removed = data.remove(key);
    listeners.forEach(l -> l.notify(key, removed, "remove"));
  }

  @Override
  public V get(K key) {
    V value = data.get(key);
    listeners.forEach(l -> l.notify(key, value, "get"));
    return value;
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    listeners.add(listener);
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    listeners.remove(listener);
  }
}
