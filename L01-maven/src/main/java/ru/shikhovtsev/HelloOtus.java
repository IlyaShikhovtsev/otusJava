package ru.shikhovtsev;

import com.google.common.math.IntMath;

import java.math.RoundingMode;

public class HelloOtus {
  public static void main(String[] args) {
    try {
      System.out.println(IntMath.mod(100, 0));
    } catch (ArithmeticException e) {
      System.out.println(e.getMessage());
    }

    System.out.println(IntMath.mod(100, 30));
    System.out.println("Логарифм " + IntMath.log10(10, RoundingMode.HALF_EVEN));
    System.out.println("Факториал " + IntMath.factorial(10));
  }
}
