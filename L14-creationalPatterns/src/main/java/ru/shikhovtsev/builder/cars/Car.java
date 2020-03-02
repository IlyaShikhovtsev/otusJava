package ru.shikhovtsev.builder.cars;

import ru.shikhovtsev.builder.Engine;
import ru.shikhovtsev.builder.GPSNavigator;
import ru.shikhovtsev.builder.Transmission;
import ru.shikhovtsev.builder.TripComputer;

public class Car {
  private final Type type;
  private final int seats;
  private final Engine engine;
  private final Transmission transmission;
  private final TripComputer tripComputer;
  private final GPSNavigator gpsNavigator;
  private double fuel = 0;

  public Car(Type type, int seats, Engine engine, Transmission transmission, TripComputer tripComputer, GPSNavigator gpsNavigator) {
    this.type = type;
    this.seats = seats;
    this.engine = engine;
    this.transmission = transmission;
    this.tripComputer = tripComputer;
    this.tripComputer.setCar(this);
    this.gpsNavigator = gpsNavigator;
  }

  public Type getType() {
    return type;
  }

  public int getSeats() {
    return seats;
  }

  public Engine getEngine() {
    return engine;
  }

  public Transmission getTransmission() {
    return transmission;
  }

  public TripComputer getTripComputer() {
    return tripComputer;
  }

  public GPSNavigator getGpsNavigator() {
    return gpsNavigator;
  }

  public void setFuel(double fuel) {
    this.fuel = fuel;
  }

  public double getFuel() {
    return fuel;
  }
}
