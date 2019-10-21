package ru.shikhovtsev.driving;

public class TripCalculator {

  private Vehicle vehicle;

  public TripCalculator(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public double calculateTime(double distance) {
    return distance / vehicle.getSpeed();
  }

  public double calculateFuelCost(double distance) {
    return distance * vehicle.getFuelCost();
  }
}
