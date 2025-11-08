package project.sort;

import project.model.Car;
import project.comparator.CarComparator;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    @Test
    void testSortByPower() {
        // Подготовка
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().power(150).model("Toyota").year(2020).build());
        cars.add(Car.builder().power(100).model("Lada").year(2015).build());
        cars.add(Car.builder().power(200).model("BMW").year(2022).build());
        
        SelectionSort<Car> sorter = new SelectionSort<>();
        CarComparator comparator = new CarComparator(CarComparator.ByField.POWER);
        
        // Действие
        sorter.sort(cars, comparator);
        
        // Проверка
        assertEquals(100, cars.get(0).getPower());
        assertEquals(150, cars.get(1).getPower());
        assertEquals(200, cars.get(2).getPower());
        
        sorter.shutdown(); // Очистка ресурсов
    }

    @Test
    void testSortByModel() {
        // Подготовка
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().power(150).model("Toyota").year(2020).build());
        cars.add(Car.builder().power(100).model("Audi").year(2015).build());
        cars.add(Car.builder().power(200).model("BMW").year(2022).build());
        
        SelectionSort<Car> sorter = new SelectionSort<>();
        CarComparator comparator = new CarComparator(CarComparator.ByField.MODEL);
        
        // Действие
        sorter.sort(cars, comparator);
        
        // Проверка
        assertEquals("Audi", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
        assertEquals("Toyota", cars.get(2).getModel());
        
        sorter.shutdown();
    }

    @Test
    void testAsyncSort() throws ExecutionException, InterruptedException {
        // Подготовка
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().power(150).model("Toyota").year(2020).build());
        cars.add(Car.builder().power(100).model("Lada").year(2015).build());
        cars.add(Car.builder().power(200).model("BMW").year(2022).build());
        
        SelectionSort<Car> sorter = new SelectionSort<>();
        CarComparator comparator = new CarComparator(CarComparator.ByField.POWER);
        
        // Действие - асинхронная сортировка
        var future = sorter.sortAsync(cars, comparator);
        future.get(); // Ждем завершения
        
        // Проверка
        assertEquals(100, cars.get(0).getPower());
        assertEquals(150, cars.get(1).getPower());
        assertEquals(200, cars.get(2).getPower());
        
        sorter.shutdown();
    }

    @Test
    void testSortEmptyList() {
        List<Car> cars = new ArrayList<>();
        SelectionSort<Car> sorter = new SelectionSort<>();
        CarComparator comparator = new CarComparator(CarComparator.ByField.POWER);
        
        // Не должно быть исключений
        sorter.sort(cars, comparator);
        assertTrue(cars.isEmpty());
        
        sorter.shutdown();
    }

    @Test
    void testSortSingleElement() {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().power(150).model("Toyota").year(2020).build());
        
        SelectionSort<Car> sorter = new SelectionSort<>();
        CarComparator comparator = new CarComparator(CarComparator.ByField.POWER);
        
        sorter.sort(cars, comparator);
        assertEquals(1, cars.size());
        assertEquals("Toyota", cars.get(0).getModel());
        
        sorter.shutdown();
    }

    @Test
    void testGetStrategyName() {
        SelectionSort<Car> sorter = new SelectionSort<>();
        assertEquals("Selection Sort (Multi-threaded)", sorter.getStrategyName());
        sorter.shutdown();
    }
}