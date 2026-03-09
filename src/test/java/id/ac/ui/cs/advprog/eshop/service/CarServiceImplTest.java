package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    CarServiceImpl carService;

    @Mock
    CarRepository carRepository;

    Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Toyota Yaris");
    }

    @Test
    void testCreate() {
        doReturn(car).when(carRepository).create(car);
        Car result = carService.create(car);
        verify(carRepository, times(1)).create(car);
        assertEquals(car.getCarId(), result.getCarId());
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        doReturn(iterator).when(carRepository).findAll();

        List<Car> result = carService.findAll();
        verify(carRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals(car.getCarId(), result.get(0).getCarId());
    }

    @Test
    void testFindById() {
        doReturn(car).when(carRepository).findById("car-123");
        Car result = carService.findById("car-123");
        verify(carRepository, times(1)).findById("car-123");
        assertEquals(car.getCarId(), result.getCarId());
    }

    @Test
    void testUpdate() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Honda Civic");

        // The service method returns void, so we just call it and verify the repository interaction
        carService.update("car-123", updatedCar);
        verify(carRepository, times(1)).update("car-123", updatedCar);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("car-123");
        verify(carRepository, times(1)).delete("car-123");
    }
}