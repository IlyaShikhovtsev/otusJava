package ru.shikhovtsev.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HWCacheDemo {
  private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

  public static void main(String[] args) {
    new HWCacheDemo().demo();
  }

  private void demo() {
    HwCache<Integer, Integer> cache = new MyCache<>();

    // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
    HwListener<Integer, Integer> listener = new HwListener<Integer, Integer>() {
      @Override
      public void notify(Integer key, Integer value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
      }
    };

    cache.addListener(listener);
    Integer value = 200;
    cache.put(value, 201);

    System.gc();

    logger.info("getValue:{}", cache.get(value));
    cache.remove(1);
    cache.removeListener(listener);
  }
}
