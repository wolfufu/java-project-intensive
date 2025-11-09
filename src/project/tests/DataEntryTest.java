package project.tests;

import project.entrydata.DataEntry;

public class DataEntryTest {
    public static void main(String[] args) {
        DataEntry entry = new DataEntry();
        boolean flag = true; // ввод с консоли - true; ввод случайных значений - false
        entry.dataEntry(flag);
    }
}
