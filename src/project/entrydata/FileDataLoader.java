package project.entrydata;

import project.model.Car;
import project.validator.CarValidator;

import java.io.*;
import java.util.*;

public class FileDataLoader {

    private final CarValidator carValidator;

    public FileDataLoader() {
        this.carValidator = new CarValidator();
    }

    public FileDataLoader(CarValidator carValidator) {
        this.carValidator = carValidator;
    }

    public List<Car> loadFromFile(String filename) {
        return loadFromFile(filename, true);
    }

    public List<Car> loadFromFile(String filename, boolean checkDuplicates) {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    Car car = parseCar(line);
                    if (car != null) {
                        cars.add(car);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка в строке " + lineNumber + ": " + line);
                    System.err.println("Причина: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        // фильтруем дубликаты
        if (checkDuplicates) {
            return removeDuplicates(cars);
        }
        return cars;
    }

    private List<Car> removeDuplicates(List<Car> cars) {
        List<Car> uniqueCars = new ArrayList<>();
        Set<String> uniqueKeys = new HashSet<>();

        for (Car car : cars) {
            String uniqueKey = car.getModel() + "_" + car.getYear();
            if (!uniqueKeys.contains(uniqueKey)) {
                uniqueCars.add(car);
                uniqueKeys.add(uniqueKey);
            } else {
                System.err.println("Обнаружен дубликат: " + car.getModel() + " " + car.getYear());
            }
        }

        if (cars.size() != uniqueCars.size()) {
            System.out.println("Удалено дубликатов: " + (cars.size() - uniqueCars.size()));
        }

        return uniqueCars;
    }

    private Car parseCar(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректный формат строки. Ожидается: мощность,модель,год");
        }

        try {
            int power = Integer.parseInt(parts[0].strip());
            String model = parts[1].strip();
            int year = Integer.parseInt(parts[2].strip());

            validateFields(power, model, year); // throws IllegalArgumentException

            return Car.builder()
                    .power(power)
                    .model(model)
                    .year(year)
                    .build();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный числовой формат");
//        } catch (IllegalArgumentException e) {
//            throw e;
        }
    }

    private void validateFields(int power, String model, int year) {

        if (!carValidator.isValidPower(power)
                || !carValidator.isValidModel(model)
                || !carValidator.isValidYear(year)) {
            String errorMessage;
            errorMessage = carValidator.getPowerErrorMessage(power).strip()
                    + carValidator.getModelErrorMessage(model).strip()
                    + carValidator.getYearErrorMessage(year).strip();
            throw new IllegalArgumentException(errorMessage);
        }
    }
}