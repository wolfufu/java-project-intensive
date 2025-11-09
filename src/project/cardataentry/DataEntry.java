package project;
import java.util.Random;
import java.util.Scanner;
import project.model.Car;


public class DataEntry {
    private Scanner scanner;
    private Random randomArray;
    private Car[] myArray;
    private int size;

    // Для случайной генерации
    private static final String[] CAR_MODELS = {
            "Toyota Camry", "Honda Accord", "BMW X5", "Mercedes E-Class",
            "Audi A4", "Ford Mustang", "Tesla Model 3", "Mazda CX-5",
            "Volkswagen Passat", "Nissan Altima", "Porsche 911", "Lexus RX",
            "Hyundai Sonata", "Kia Optima", "Subaru Outback"
    };

    public DataEntry() {
        this.scanner = new Scanner(System.in);
        this.randomArray = new Random();
    }

    public void dataEntry(){

        System.out.println("Введите размер массива: ");
        size = scanner.nextInt();
        scanner.nextLine();

        if (size <= 0) {
            System.out.println("Размер должен быть положительным.");
            return;
        }
        myArray = new Car[size];

        System.out.println("Выберите способ заполнение массива:");
        System.out.println("1. Ввести вручную");
        System.out.println("2. Заполнить случайно");
        int way = scanner.nextInt();
        scanner.nextLine();

        switch (way){
            case 1:
                fillArrayManually();
                break;
            case 2:
                fillArrayRandomly();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }
        displayMyArray();
    }
    private void fillArrayManually() {
        System.out.println("\nВведите данные для " + size + " машины:");
        for (int i = 0; i < size; i++) {
            System.out.println("\n--- Машина " + (i + 1) + " ---");
            boolean validCar = false;

            while (!validCar) {
                try {
                    System.out.print("Модель: ");
                    String model = scanner.nextLine();

                    System.out.print("Мощность (л.с.): ");
                    int power = scanner.nextInt();

                    System.out.print("Год выпуска: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // очистка буфера

                    // Используем Builder из вашего класса Car
                    myArray[i] = Car.builder()
                            .model(model)
                            .power(power)
                            .year(year)
                            .build();

                    validCar = true;
                    System.out.println("✓ Машина добавлена");

                } catch (IllegalArgumentException e) {
                    System.out.println("✗ Ошибка валидации: " + e.getMessage());
                    System.out.println("Попробуйте снова.");
                    scanner.nextLine(); // очистка при ошибке
                } catch (Exception e) {
                    System.out.println("✗ Неверный формат ввода!");
                    scanner.nextLine();
                }
            }
        }
    }
    private void fillArrayRandomly() {
        System.out.println("Ввести максимальное и минимальное значение? " + "1 - да" + "2 - нет");
        int restrictions = scanner.nextInt();
        if (restrictions == 1) {
            fillWithCustomRange();
        } else {
            fillWithDefaultRange();
        }

        System.out.println("✓ Массив заполнен " + size + " машинами!");
    }

    private void fillWithCustomRange() {
        System.out.print("Минимальная мощность (л.с.): ");
        int minPower = scanner.nextInt();
        System.out.print("Максимальная мощность (л.с.): ");
        int maxPower = scanner.nextInt();

        System.out.print("Минимальный год выпуска: ");
        int minYear = scanner.nextInt();
        System.out.print("Максимальный год выпуска: ");
        int maxYear = scanner.nextInt();

        if (minPower > maxPower || minYear > maxYear) {
            System.out.println("Неверные диапазоны! Использую значения по умолчанию.");
            fillWithDefaultRange();
            return;
        }

        for (int i = 0; i < myArray.length; i++) {
            boolean created = false;
            int attempts = 0;

            while (!created && attempts < 10) {
                try {
                    String model = CAR_MODELS[randomArray.nextInt(CAR_MODELS.length)];
                    int power = minPower + randomArray.nextInt(maxPower - minPower + 1);
                    int year = minYear + randomArray.nextInt(maxYear - minYear + 1);

                    myArray[i] = Car.builder()
                            .model(model)
                            .power(power)
                            .year(year)
                            .build();

                    created = true;
                } catch (IllegalArgumentException e) {
                    attempts++;
                }
            }

            if (!created) {
                // Если не удалось создать с заданными параметрами, используем безопасные
                myArray[i] = createDefaultCar();
            }
        }
    }

    private void fillWithDefaultRange() {
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = createDefaultCar();
        }
    }

    private Car createDefaultCar() {
        String model = CAR_MODELS[randomArray.nextInt(CAR_MODELS.length)];
        int power = 100 + randomArray.nextInt(400);  // 100-499 л.с.
        int year = 2000 + randomArray.nextInt(25);   // 2000-2024

        return Car.builder()
                .model(model)
                .power(power)
                .year(year)
                .build();
    }

    private void displayMyArray() {
        System.out.println("\n=== СОЗДАННЫЙ МАССИВ МАШИН ===");
        System.out.println("Количество: " + myArray.length);
        System.out.println("\nСписок машин:");

        for (int i = 0; i < myArray.length; i++) {
            System.out.printf("%d. %s%n", i + 1, myArray[i]);
        }

        // Статистика
        if (myArray.length > 0) {
            System.out.println("\n--- Статистика ---");

            // Средняя мощность
            int totalPower = 0;
            int minPower = myArray[0].getPower();
            int maxPower = myArray[0].getPower();
            int oldestYear = myArray[0].getYear();
            int newestYear = myArray[0].getYear();

            for (Car car : myArray) {
                totalPower += car.getPower();
                minPower = Math.min(minPower, car.getPower());
                maxPower = Math.max(maxPower, car.getPower());
                oldestYear = Math.min(oldestYear, car.getYear());
                newestYear = Math.max(newestYear, car.getYear());
            }

            System.out.printf("Средняя мощность: %.1f л.с.%n",
                    (double) totalPower / myArray.length);
            System.out.println("Диапазон мощности: " + minPower + " - " + maxPower + " л.с.");
            System.out.println("Годы выпуска: " + oldestYear + " - " + newestYear);
        }
    }

    // Геттер для получения массива машин
    public Car[] getCarArray() {
        return myArray;
    }

    // Метод для тестирования
    public static void main(String[] args) {
        DataEntry entry = new DataEntry();
        entry.dataEntry();
    }
}

