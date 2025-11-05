package project.model;

import project.validator.Validator;
import project.validator.CarValidator;

public class Car {
    private final int power;
    private final String model;
    private final int year;

    private Car(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("Car{power=%d, model='%s', year=%d}", power, model, year);
    }

    public static class Builder {
        private int power;
        private String model;
        private int year;
        private final Validator<Car> validator;

        public Builder() {
            this.validator = new CarValidator();
        }

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            Car car = new Car(this);
            if (!validator.isValid(car)) {
                throw new IllegalArgumentException("Car validation failed: " +
                        validator.getErrorMessage(car));
            }
            return car;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}