package ru.iruchidesu.weathertest;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}