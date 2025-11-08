package project.sort;

import project.comparator.CarComparator;
import project.model.Car;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SortManager {
    private SortingStrategy<Car> currentStrategy;

    public void setSortingStrategy(SortingStrategy<Car> strategy) {
        this.currentStrategy = strategy;
    }

     
    public void sort(List<Car> cars, CarComparator.ByField field) {
        if (currentStrategy == null) {
            throw new IllegalStateException("Стратегия сортировки не установлена");
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("Массив пуст. Сортировка не требуется.");
            return;
        }

        Comparator<Car> comparator = new CarComparator(field);
        System.out.println("Начало синхронной сортировки: " + currentStrategy.getStrategyName());
        
        long startTime = System.currentTimeMillis();
        currentStrategy.sort(cars, comparator);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Сортировка завершена за " + (endTime - startTime) + " мс");
    }

     
    public CompletableFuture<Void> sortAsync(List<Car> cars, CarComparator.ByField field) {
        if (currentStrategy == null) {
            throw new IllegalStateException("Стратегия сортировки не установлена");
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("Массив пуст. Сортировка не требуется.");
            return CompletableFuture.completedFuture(null);
        }

        Comparator<Car> comparator = new CarComparator(field);
        System.out.println("Начало асинхронной сортировки: " + currentStrategy.getStrategyName());
        System.out.println("Используется ThreadPool с несколькими потоками");
        
        long startTime = System.currentTimeMillis();
        return currentStrategy.sortAsync(cars, comparator)
                .thenRun(() -> {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Асинхронная сортировка завершена за " + (endTime - startTime) + " мс");
                });
    }

    public String getCurrentStrategyName() {
        return currentStrategy != null ? currentStrategy.getStrategyName() : "Не установлена";
    }
}