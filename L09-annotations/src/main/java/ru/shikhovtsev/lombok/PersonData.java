package ru.shikhovtsev.lombok;

import lombok.Data;

import java.util.List;

@Data
public class PersonData {

  int age;
  String name;
  String address;
  List<String> children;

}
