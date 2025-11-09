package project.tests;

import project.entrydata.FileDataLoader;
import project.model.Car;
import project.validator.CarValidator;

import java.util.List;

public class FileDataLoaderTest {

    private FileDataLoader fileDataLoader;

    public FileDataLoaderTest() {
        this.fileDataLoader = new FileDataLoader();
    }

    private void testValidFile() {
        System.out.println("1. Тест с корректными данными:");

        String testFile = "cars.txt";

        List<Car> cars = fileDataLoader.loadFromFile(testFile, false);
        System.out.println("   Загружено автомобилей: " + cars.size());
        for (Car car : cars) {
            System.out.println("   - " + car);
        }
        
    }

    private void testFileWithInvalidData() {
        System.out.println("2. Тест с некорректными данными:");

        String testFile = "invalid_cars.txt";

        List<Car> cars = fileDataLoader.loadFromFile(testFile, false);
        System.out.println("   Успешно загружено: " + cars.size() + " автомобилей");

    }

    private void testNonExistentFile() {
        System.out.println("3. Тест с несуществующим файлом:");

        List<Car> cars = fileDataLoader.loadFromFile("non_existent_file.txt", true);
        System.out.println("   Загружено из несуществующего файла: " +
                (cars != null ? cars.size() : "null"));

    }

    private void testEmptyFile() {
        System.out.println("4. Тест с пустым файлом:");

        List<Car> cars = fileDataLoader.loadFromFile("test_empty.txt", true);
        System.out.println("   Загружено из пустого файла: " + cars.size());

    }

    public static void main(String[] args) {
        FileDataLoaderTest tester = new FileDataLoaderTest();

        tester.testValidFile();
        tester.testFileWithInvalidData();
        tester.testNonExistentFile();
        tester.testEmptyFile();

    }
}