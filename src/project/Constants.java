package project;

import java.time.LocalDate;

public final class Constants {
    private Constants() {}

    public static final int MIN_PROD_YEAR = 1885;
    public static final int MAX_PROD_YEAR = LocalDate.now().getYear();
    public static final int MIN_POWER = 20;
    public static final int MAX_POWER = 2000;
    public static final String[] MODELS_LIST = {"Toyota", "Tesla", "Audi", "Lada", "BMW", "WV"};
    public static final String DEFAULT_FILENAME = "cars.txt";
}
