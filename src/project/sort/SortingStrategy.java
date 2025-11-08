package project.sort;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SortingStrategy<T> {
    CompletableFuture<Void> sortAsync(List<T> list, Comparator<T> comparator);
    void sort(List<T> list, Comparator<T> comparator);
    String getStrategyName();
    void shutdown(); // Добавляем метод завершения
}