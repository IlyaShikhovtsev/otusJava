package ru.shikhovtsev;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shikhovtsev.json.DIYJsonObjectWriter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DIYJsonObjectWriterTest {

  private static DIYJsonObjectWriter jsonWriter;
  private static Gson gson;

  @BeforeAll
  static void init() {
    jsonWriter = new DIYJsonObjectWriter();
    gson = new Gson();
  }

  @Test
  @DisplayName("array int method works correctly")
  void arrayInt() {
    int[] array = {1, 5, 6, 3, 2};
    assertEquals(gson.toJson(array), jsonWriter.toJson(array));
  }

  @Test
  @DisplayName("long method works correctly")
  void arrayLong() {
    long[] array = {1, 5, 6, 3, 2};
    assertEquals(gson.toJson(array), jsonWriter.toJson(array));
  }

  @Test
  @DisplayName("ToJson method works correctly")
  void arrayDouble() {
    double[] array = {1d, 5d, 6d, 3d, 2d};
    assertEquals(gson.toJson(array), jsonWriter.toJson(array));
  }

  @Test
  @DisplayName("BagPrimitives to json method works correctly")
  void bagPrimitivesToJson() {
    var bag = new BagOfPrimitives(22, "test", 10);
    var json = jsonWriter.toJson(bag);

    assertEquals(gson.toJson(bag), json);
    assertEquals(bag, gson.fromJson(json, BagOfPrimitives.class));
  }

  @Test
  @DisplayName("Collection of BagPrimitives method works correctly")
  void bagPrimitives() {
    var bag = new BagOfPrimitives(22, "test", 10);
    var bag1 = new BagOfPrimitives(23, "test1", 103);
    var bag2 = new BagOfPrimitives(24, "test2", 104);
    List<BagOfPrimitives> bags = List.of(bag, bag1, bag2);

    String json = jsonWriter.toJson(bags);
    System.out.println(json);

    String gsonStr = gson.toJson(bags);
    System.out.println(gsonStr);

    assertEquals(gsonStr, json);

    var bagsFromJsom = gson.fromJson(json, new TypeToken<List<BagOfPrimitives>>(){}.getType());
    assertEquals(bags, bagsFromJsom);
  }
}
