package ru.shikhovtsev;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.shikhovtsev.json.DIYJsonObjectWriter;

import java.util.List;

public class JsonDemo {
  public static void main(String[] args) {
    var jsonWriter = new DIYJsonObjectWriter();
    var gson = new Gson();

    var bag = new BagOfPrimitives(22, "test", 10);
    var bag1 = new BagOfPrimitives(23, "test1", 103);
    var bag2 = new BagOfPrimitives(24, "test2", 104);
    List<BagOfPrimitives> bags = List.of(bag, bag1, bag2);


    String gsonStr = gson.toJson(bags);
    System.out.println(gsonStr);

    String json = jsonWriter.toJson(bags);
    System.out.println(json);

    var obj1 = gson.fromJson(gsonStr, List.class);
    System.out.println(bags.equals(obj1));
    System.out.println(obj1);

    var obj2 = gson.fromJson(json, new TypeToken<List<BagOfPrimitives>>(){}.getType());
    System.out.println(bags.equals(obj2));
    System.out.println(obj2);
  }
}
