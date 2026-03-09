package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota Yaris");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        Car savedCar = carRepository.create(car);
        assertNotNull(savedCar.getCarId()); // ID should be auto-generated
        assertEquals("Toyota Yaris", savedCar.getCarName());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Honda Civic");

        Car savedCar = carRepository.create(car);
        assertEquals("car-123", savedCar.getCarId()); // ID should not change
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        car1.setCarId("car-1");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("car-2");
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        assertEquals("car-1", iterator.next().getCarId());
        assertEquals("car-2", iterator.next().getCarId());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        Car foundCar = carRepository.findById("car-123");
        assertNotNull(foundCar);
        assertEquals("car-123", foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Old Name");
        car.setCarColor("Old Color");
        car.setCarQuantity(1);
        carRepository.create(car);

        Car updatedCarData = new Car();
        updatedCarData.setCarName("New Name");
        updatedCarData.setCarColor("New Color");
        updatedCarData.setCarQuantity(10);

        Car result = carRepository.update("car-123", updatedCarData);
        assertNotNull(result);
        assertEquals("New Name", result.getCarName());
        assertEquals("New Color", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        Car updatedCarData = new Car();
        updatedCarData.setCarName("New Name");

        Car result = carRepository.update("non-existent-id", updatedCarData);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("car-123");
        carRepository.create(car);

        assertNotNull(carRepository.findById("car-123"));

        carRepository.delete("car-123");
        assertNull(carRepository.findById("car-123"));
    }
}