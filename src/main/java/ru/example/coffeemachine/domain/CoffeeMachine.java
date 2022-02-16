package ru.example.coffeemachine.domain;

public interface CoffeeMachine {
    void turnOn();

    void checkResources();

    Boolean isCheckedResources();

    void pushStartBrew();

    void brewCoffee();

    void turnOff();
}
