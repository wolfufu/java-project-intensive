package project.entrydata;

import project.model.Car;
import project.validator.CarValidator;
import project.validator.Validator;

import java.io.*;
import java.util.*;

public class FileDataLoader {

    private final Validator<Car> carValidator;

    public FileDataLoader() {
        this.carValidator = new CarValidator();
    }

    public FileDataLoader(Validator<Car> carValidator) {
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
                    if (!carValidator.isValid(car)) {
                        throw new IllegalArgumentException(carValidator.getErrorMessage(car));
                    }
                    cars.add(car);
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
            int power = Integer.parseInt(parts[0].trim());
            String model = parts[1].trim();
            int year = Integer.parseInt(parts[2].trim());

            return Car.builder()
                    .power(power)
                    .model(model)
                    .year(year)
                    .build();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный числовой формат");
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}