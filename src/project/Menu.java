package project;

import java.util.Scanner;

public class Menu {

    private boolean isRunning;
    private final Scanner scanner;

    public Menu() {
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (isRunning) {
            printMenu();
            int choice = getIntInput("Ввод команды: ");
            handleMenuChoice(choice);
        }
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
                // заполнить массив данных
                break;
            case 2:
                // стратегия сортировки
                break;
            case 3:
                // вывод массива
                break;
            case 4:
                exit();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
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
}
