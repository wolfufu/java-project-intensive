package project;

import project.model.Car;
import project.entrydata.FileDataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private boolean isRunning;
    private final Scanner scanner;
    private final List<Car> cars; // текущий массив данных
    private final FileDataLoader fileDataLoader;

    public Menu() {
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
        this.fileDataLoader = new FileDataLoader();
        this.cars = new ArrayList<>();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void run() {
        while (isRunning) {
            printMenu();
            int choice = getIntInput("Ввод команды: ");
            handleMenuChoice(choice);
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n=== КОНСОЛЬНОЕ ПРИЛОЖЕНИЕ ===");
        System.out.println("1. Заполнить массив данных");
        System.out.println("2. Выбрать стратегию сортировки");
        System.out.println("3. Вывести текущий массив");
        System.out.println("4. Выйти из программы");
        System.out.println("================================");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                fillDataArray();
                break;
            case 2:
                // стратегия сортировки
                break;
            case 3:
                printCurrentArray();
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void fillDataArray() {
        System.out.println("\n=== СПОСОБ ЗАПОЛНЕНИЯ ===");
        System.out.println("1. Загрузить из файла");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Ручной ввод");
        System.out.println("4. Назад в главное меню");

        int choice = getIntInput("Выберите способ: ");

        switch (choice) {
            case 1:
                addNewElements();
                break;
            case 2:
                // рандом
                break;
            case 3:
                // консоль
                break;
            case 4:
                System.out.println("Возвращаем в главное меню...");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void addNewElements() {
        System.out.println("\n=== ЗАПОЛНЕНИЕ ===");
        System.out.println("1. Перезаписать массив");
        System.out.println("2. Добавить к существующему");

        int choice = getIntInput("Выберите вариант: ");

        switch (choice) {
            case 1:
                loadFromFile(false);
                break;
            case 2:
                loadFromFile(true);
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void loadFromFile(boolean addFlag) {
        System.out.println("\n=== ФАЙЛ С ДАННЫМИ ===");
        System.out.println("1. Файл по умолчанию");
        System.out.println("2. Пользовательский файл");

        int intChoice = getIntInput("Выберите вариант: ");

        String filename;

        switch (intChoice) {
            case 1:
                filename = Constants.DEFAULT_FILENAME;
                break;
            case 2:
                System.out.print("Введите имя файла: "); // Введите имя файла: cars.txt
                filename = scanner.nextLine();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        boolean checkDuplicates;
        String stringChoice = getStringInput("Добавлять дубликаты? (y/n)");

        switch (stringChoice) {
            case "y":
                checkDuplicates = false;
                break;
            case "n":
                checkDuplicates = true;
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }


        List<Car> loadedCars = fileDataLoader.loadFromFile(filename, checkDuplicates);

        if (loadedCars != null && !loadedCars.isEmpty()) {
            if (!addFlag) {
                this.cars.clear();
                System.out.println("Массив очищен. ");
            }
            this.cars.addAll(loadedCars);
            System.out.println("Успешно загружено " + loadedCars.size() + " автомобилей из файла.");
            System.out.println("Всего в массиве: " + cars.size() + " автомобилей.");
        } else {
            System.out.println("Не удалось загрузить данные из файла.");
        }
    }

    private void printCurrentArray() {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Массив данных пуст.");
            return;
        }

        System.out.println("\n=== ТЕКУЩИЙ МАССИВ АВТОМОБИЛЕЙ ===");
        System.out.println("Количество элементов: " + cars.size());
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
    }

    private void exit() {
        this.isRunning = false;
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private String getStringInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    return input;
                } else {
                    System.out.println("Ошибка: ввод не может быть пустым.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }
}
