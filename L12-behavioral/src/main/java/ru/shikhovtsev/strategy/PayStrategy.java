package ru.shikhovtsev.strategy;

public interface PayStrategy {
  boolean pay(int paymentAmount);
  void collectPaymentDetails();
}
