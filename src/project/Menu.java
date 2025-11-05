package project;

import project.model.Car;
import project.entrydata.FileDataLoader;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private boolean isRunning;
    private final Scanner scanner;
    private List<Car> cars; // Текущий массив данных
    private final FileDataLoader fileDataLoader;

    public Menu() {
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
        this.fileDataLoader = new FileDataLoader();
        this.cars = null; // массив пуст
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
        System.out.println("\n=== ВЫБОР СПОСОБА ЗАПОЛНЕНИЯ ===");
        System.out.println("1. Загрузить из файла");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Ручной ввод");
        System.out.println("4. Назад в главное меню");

        int choice = getIntInput("Выберите способ: ");

        switch (choice) {
            case 1:
                loadFromFile();
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

    private void loadFromFile() {
        System.out.print("Введите имя файла: ");
        String filename = scanner.nextLine();

        List<Car> loadedCars = fileDataLoader.loadFromFile(filename);

        if (loadedCars != null && !loadedCars.isEmpty()) {
            this.cars = loadedCars;
            System.out.println("Успешно загружено " + cars.size() + " автомобилей из файла.");
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
            } catch (NumberFormatException eInput) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }
}
