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
    Vehicle spy = spy(new Car(10));
    doReturn(1d).when(spy).getFuelCost();

    double result = new TripCalculator(spy).calculateFuelCost(50);

    assertEquals(50, result);
    verify(spy, times(1)).getFuelCost();
    verify(spy, never()).getSpeed();
  }

}
