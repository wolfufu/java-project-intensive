package project.sort;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SelectionSort<T> implements SortingStrategy<T> {
    private final ExecutorService executor;
    
    public SelectionSort() {
        // Создаем ThreadPool с минимум 2 потоками
        this.executor = Executors.newFixedThreadPool(2);
    }
    
    @Override
    public CompletableFuture<Void> sortAsync(List<T> list, Comparator<T> comparator) {
        return CompletableFuture.runAsync(() -> {
            System.out.println("Сортировка выполняется в потоке: " + Thread.currentThread().getName());
            sort(list, comparator);
        }, executor);
    }

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) return;
        
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                T temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

    @Override
    public String getStrategyName() {
        return "Selection Sort (Multi-threaded)";
    }
    
    @Override
    public void shutdown() {
        executor.shutdown();
        System.out.println("ThreadPool SelectionSort завершен");
    }
}