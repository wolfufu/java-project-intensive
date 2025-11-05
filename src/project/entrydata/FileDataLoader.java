package project.entrydata;

import project.model.Car;
import java.io.*;
import java.util.*;

public class FileDataLoader {

    public List<Car> loadFromFile(String filename) {

        // filename должен быть полным путем до файла, т.е. C:\Users\имя_пользователя\IdeaProjects\java-project-intensive\src\project\название_файла

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

        return cars;
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