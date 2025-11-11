package project;

import project.model.Car;
import project.entrydata.FileDataLoader;
import project.entrydata.DataEntry; // Добавляем импорт
import project.sort.SelectionSort;
import project.sort.SortManager;
import project.comparator.CarComparator;
import project.validator.CarValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Menu {

    private boolean isRunning;
    private final Scanner scanner;
    private final List<Car> cars; // текущий массив данных
    private final FileDataLoader fileDataLoader;
    private final SortManager sortManager;
    private final SelectionSort<Car> selectionSort; // Сохраняем для shutdown
    private final DataEntry dataEntry;
    private final CarValidator carValidator;

    public Menu() {
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
        this.fileDataLoader = new FileDataLoader();
        this.cars = new ArrayList<>();
        this.selectionSort = new SelectionSort<>(); // Сохраняем экземпляр
        this.sortManager = new SortManager();
        this.sortManager.setSortingStrategy(selectionSort);
        this.dataEntry = new DataEntry();
        this.carValidator = new CarValidator();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void shutdown() {
        selectionSort.shutdown();
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
                selectSortingStrategy();
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

    private void selectSortingStrategy() {
        if (cars.isEmpty()) {
            System.out.println("Массив данных пуст. Заполните массив перед сортировкой.");
            return;
        }

        System.out.println("\n=== ВЫБОР ПОЛЯ ДЛЯ СОРТИРОВКИ ===");
        System.out.println("Текущая стратегия: " + sortManager.getCurrentStrategyName());
        System.out.println("1. По мощности (power)");
        System.out.println("2. По модели (model)");
        System.out.println("3. По году выпуска (year)");
        System.out.println("4. Назад в главное меню");

        int choice = getIntInput("Выберите поле для сортировки: ");

        CarComparator.ByField field = null;
        switch (choice) {
            case 1:
                field = CarComparator.ByField.POWER;
                break;
            case 2:
                field = CarComparator.ByField.MODEL;
                break;
            case 3:
                field = CarComparator.ByField.YEAR;
                break;
            case 4:
                return;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        // Выбор типа сортировки
        System.out.println("\n=== ТИП СОРТИРОВКИ ===");
        System.out.println("1. Синхронная (блокирующая)");
        System.out.println("2. Асинхронная (многопоточная)");
        int sortType = getIntInput("Выберите тип сортировки: ");

        try {
            List<Car> carsCopy = new ArrayList<>(cars);

            System.out.println("\n--- ДО СОРТИРОВКИ ---");
            printFirstAndLast(carsCopy);

            if (sortType == 1) {
                // Синхронная сортировка
                sortManager.sort(carsCopy, field);
            } else if (sortType == 2) {
                // Асинхронная сортировка
                System.out.println("Запуск асинхронной сортировки в ThreadPool...");
                CompletableFuture<Void> sortFuture = sortManager.sortAsync(carsCopy, field);

                // Показываем прогресс (можно делать другие операции)
                System.out.println("Сортировка выполняется в фоне...");
                System.out.println("Можно продолжать работу (в реальном приложении)");

                // Ждем завершения (для демонстрации)
                sortFuture.join(); // Блокируем до завершения
            } else {
                System.out.println("Неверный выбор типа сортировки.");
                return;
            }

            System.out.println("\n--- ПОСЛЕ СОРТИРОВКИ ---");
            printFirstAndLast(carsCopy);

            cars.clear();
            cars.addAll(carsCopy);
            System.out.println("\nСортировка завершена успешно!");

        } catch (Exception e) {
            System.out.println("Ошибка при сортировке: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printFirstAndLast(List<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }

        int showCount = Math.min(3, carList.size());
        System.out.println("Первые " + showCount + " элемента:");
        for (int i = 0; i < showCount; i++) {
            System.out.println("  " + carList.get(i));
        }

        if (carList.size() > showCount * 2) {
            System.out.println("...");
            System.out.println("Последние " + showCount + " элемента:");
            for (int i = carList.size() - showCount; i < carList.size(); i++) {
                System.out.println("  " + carList.get(i));
            }
        }
    }

    private void fillDataArray() {
        System.out.println("\n=== СПОСОБ ЗАПОЛНЕНИЯ ===");
        System.out.println("1. Загрузить из файла");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Ручной ввод");
        System.out.println("4. Назад в главное меню");

        int choice = getIntInput("Выберите способ: ");
        boolean flag = addNewElements();

        switch (choice) {
            case 1:
                loadFromFile(flag);
                break;
            case 2:
                generateRandomData(flag);
                break;
            case 3:
                enterManualData(flag);
                break;
            case 4:
                System.out.println("Возвращаем в главное меню...");
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void generateRandomData(boolean addFlag) {
        System.out.println("\n=== СЛУЧАЙНАЯ ГЕНЕРАЦИЯ ДАННЫХ ===");

        if (!addFlag) {
            cars.clear();
            System.out.println("Массив очищен.");
        }

        dataEntry.dataEntry(false);

        Car[] generatedCars = dataEntry.getCarArray();
        if (generatedCars != null && generatedCars.length > 0) {
            int i = 0;
            for (Car car : generatedCars) {
                i++;
                if (carValidator.isValid(car)) {
                    cars.add(car);
                } else {
                    System.out.println("Невалидный автомобиль: " + car + "; Errors: " + carValidator.getErrorMessage(car));
                    i--;
                }
            }
            System.out.println("Получено " + generatedCars.length + " случайно сгенерированных автомобилей.");
            System.out.println("Успешно добавлено " + i + " автомобилей.");
            System.out.println("Всего в массиве: " + cars.size() + " автомобилей.");
        } else {
            System.out.println("Не удалось сгенерировать данные.");
        }
    }

    private void enterManualData(boolean addFlag) {
        System.out.println("\n=== РУЧНОЙ ВВОД ДАННЫХ ===");

        if (!addFlag) {
            cars.clear();
            System.out.println("Массив очищен.");
        }

        dataEntry.dataEntry(true);

        Car[] manualCars = dataEntry.getCarArray();
        if (manualCars != null && manualCars.length > 0) {
            for (Car car : manualCars) {
                cars.add(car);
            }
            System.out.println("Успешно добавлено " + manualCars.length + " автомобилей.");
            System.out.println("Всего в массиве: " + cars.size() + " автомобилей.");
        } else {
            System.out.println("Не удалось ввести данные.");
        }
    }

    private boolean addNewElements() {
        System.out.println("\n=== ЗАПОЛНЕНИЕ ===");
        System.out.println("1. Перезаписать массив");
        System.out.println("2. Добавить к существующему");

        int choice = getIntInput("Выберите вариант: ");

        switch (choice) {
            case 1:
                return false;
            case 2:
                return true;
            default:
                System.out.println("Неверный выбор.");
        }
        return false;
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
                System.out.print("Введите имя файла: ");
                filename = scanner.nextLine();
                break;
            default:
                System.out.println("Неверный выбор.");
                return;
        }

        boolean checkDuplicates;
        String stringChoice = getStringInput("Добавлять дубликаты? (y/n): ");

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
                System.out.println("Массив очищен.");
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
        System.out.println("Программа завершена. До свидания!");
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