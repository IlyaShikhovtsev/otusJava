package ru.shikhovtsev.lombok;

import lombok.Value;

import java.util.List;

@Value
public class PersonValue {

  int age;
  String name;
  String address;
  List<String> children;

}
