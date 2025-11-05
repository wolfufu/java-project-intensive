package project.validator;

public interface Validator<T> {
    boolean isValid(T object);
    String getErrorMessage(T object);
}