package bll.validators;

public interface Validator<T> {

    void validate(T t) throws Exception;
}