package com.khalchukov.exception;

public class SmartphoneNotFoundException extends RuntimeException {

    public SmartphoneNotFoundException(int id) {
        super("Смартфон с id=" + id + " не найден");
    }

    public SmartphoneNotFoundException(String model) {
        super("Смартфон с моделью '" + model + "' не найден");
    }
}
