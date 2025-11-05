package project.comparator;

import project.model.Car;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {
    private final CarComparatorFactory.ByField byField;

    public CarComparator(CarComparatorFactory.ByField byField) {
        this.byField = byField;
    }

    @Override
    public int compare(Car o1, Car o2) {

        int result = switch (byField) {
            case POWER -> Integer.compare(o1.getPower(), o2.getPower());
            case MODEL -> o1.getModel().compareTo(o2.getModel());
            case PRODUCTION_YEAR -> Integer.compare(o1.getYear(), o2.getYear());
        };
        if (result != 0) {
            return result;
        }

        result = o1.getModel().compareTo(o2.getModel());
        if (result != 0) return result;

        result = Integer.compare(o1.getPower(), o2.getPower());
        if (result != 0) return result;

        return Integer.compare(o1.getYear(), o2.getYear());
    }
}

