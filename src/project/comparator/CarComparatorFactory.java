package project.comparator;

import project.model.Car;

import java.util.Comparator;

public class CarComparatorFactory {

    public enum ByField {
        POWER,
        MODEL,
        YEAR
    }

    public Comparator<Car> getCarComparator(ByField sortByField) {
        return switch (sortByField) {
            case POWER -> new CarComparator(ByField.POWER);
            case MODEL -> new CarComparator(ByField.MODEL);
            case YEAR -> new CarComparator(ByField.YEAR);
        };
    }
}


