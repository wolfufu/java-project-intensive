package project.validator;

import project.Constants;
import project.model.Car;

public class CarValidator implements Validator<Car> {

    private static final String POWER_ERROR_TEMPLATE = "Invalid power '%d'. Must be between %d and %d. ";
    private static final String MODEL_ERROR_TEMPLATE = "Invalid model: '%s'. Model cannot be empty. ";
    private static final String YEAR_ERROR_TEMPLATE = "Invalid year: '%d'. Must be between %d and %d. ";

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
        int power = car.getPower();
        String model = car.getModel();
        int year = car.getYear();

        error.append(getPowerErrorMessage(power));
        error.append(getModelErrorMessage(model));
        error.append(getYearErrorMessage(year));

        return !error.isEmpty() ? error.toString() : "Valid";
    }

    public String getPowerErrorMessage(int power) {
        return isValidPower(power) ? "" : String.format(POWER_ERROR_TEMPLATE, power, Constants.MIN_POWER, Constants.MAX_POWER);
    }

    public String getModelErrorMessage(String model) {
        return isValidModel(model) ? "" : String.format(MODEL_ERROR_TEMPLATE, model);
    }

    public String getYearErrorMessage(int year) {
        return isValidYear(year) ? "" : String.format(YEAR_ERROR_TEMPLATE, year, Constants.MIN_YEAR, Constants.MAX_YEAR);
    }

    public boolean isValidPower(int power) {
        return power >= Constants.MIN_POWER && power <= Constants.MAX_POWER;
    }

    public boolean isValidModel(String model) {
        return model != null && !model.isBlank();
    }

    public boolean isValidYear(int year) {
        return year >= Constants.MIN_YEAR && year <= Constants.MAX_YEAR;
    }
}