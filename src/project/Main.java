package project;

import project.model.Car;
import project.validator.CarValidator;

public class Main {
    public static void main(String[] args) {
        // Тестируем классы
        testCarAndValidator();

        // Существующий код
        Menu menu = new Menu();
        menu.run();
    }

    private static void testCarAndValidator() {
        System.out.println("=== Testing Car and Validator ===");

        // Тест 1: Валидный автомобиль
        Car car1 = Car.builder()
                .power(150)
                .model("Toyota Camry")
                .year(2020)
                .build();
        System.out.println("Valid car: " + car1);

        // Тест 2: Невалидный автомобиль
        try {
            Car.builder()
                    .power(10)
                    .model("")
                    .year(1800)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // Тест 3: Проверка валидатора отдельно
        CarValidator validator = new CarValidator();
        Car testCar = Car.builder().power(100).model("Test").year(2020).build();
        System.out.println("Car validation: " + validator.isValid(testCar));
        System.out.println("Validation message: " + validator.getErrorMessage(testCar));
    }
}