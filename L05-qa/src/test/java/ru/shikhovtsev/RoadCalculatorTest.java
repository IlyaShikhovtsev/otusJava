package ru.shikhovtsev;

import org.junit.jupiter.api.Test;
import ru.shikhovtsev.driving.Car;
import ru.shikhovtsev.driving.TripCalculator;
import ru.shikhovtsev.driving.Vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoadCalculatorTest {

  @Test
  void test() {
    int distance = 50;
    int speed = 10;

    Vehicle spy = spy(new Car(speed));
    doReturn(1d).when(spy).getFuelCost();

    TripCalculator calculator = new TripCalculator(spy);
    double fuelCost = calculator.calculateFuelCost(distance);
    double tripTime = calculator.calculateTime(distance);

    assertEquals(distance, fuelCost);
    verify(spy, times(1)).getFuelCost();

    assertEquals(distance / speed, tripTime);
    verify(spy, times(1)).getSpeed();
  }

}
