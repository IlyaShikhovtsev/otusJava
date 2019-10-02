package ru.shikhovtsev.shade;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    List<Integer> example = new ArrayList<>();
    IntStream.range(1, 10).forEach(example::add);
    List<Integer> result = Lists.reverse(example);
    System.out.println(result);

    System.out.println("Hello, World. Shade test!");
  }
}
