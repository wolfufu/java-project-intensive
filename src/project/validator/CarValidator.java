package project.validator;

import project.model.Car;

public class CarValidator implements Validator<Car> {

    @Override
    public boolean isValid(Car car) {
        return car != null &&
                isValidPower(car.getPower()) &&
                isValidModel(car.getModel()) &&
                isValidYear(car.getYear());
    }

    @Override
    public String getErrorMessage(Car car) {
        if (car == null) {
            return "Car cannot be null";
        }

        StringBuilder error = new StringBuilder();

        if (!isValidPower(car.getPower())) {
            error.append("Invalid power: ").append(car.getPower())
                    .append(". Must be between 50 and 2000 hp. ");
        }

        if (!isValidModel(car.getModel())) {
            error.append("Invalid model: ").append(car.getModel())
                    .append(". Model cannot be empty. ");
        }

        if (!isValidYear(car.getYear())) {
            error.append("Invalid year: ").append(car.getYear())
                    .append(". Must be between 1900 and 2024. ");
        }

        return !error.isEmpty() ? error.toString() : "Valid";
    }

    private boolean isValidPower(int power) {
        return power >= 50 && power <= 2000;
    }

    private boolean isValidModel(String model) {
        return model != null && !model.trim().isEmpty();
    }

    private boolean isValidYear(int year) {
        return year >= 1900 && year <= 2024;
    }
}