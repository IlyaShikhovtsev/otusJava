package ru.shikhovtsev.driving;

public class Car implements Vehicle {

  private int speed;

  public Car(int speed) {
    this.speed = speed;
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public double getFuelCost() {
    throw new UnsupportedOperationException();
  }

}
