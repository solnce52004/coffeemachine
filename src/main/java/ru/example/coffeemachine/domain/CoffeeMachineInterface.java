package ru.example.coffeemachine.domain;

public interface CoffeeMachineInterface {
    void turnOn();

    void checkResources();

    Boolean isCheckedResources();

    void pushStartBrew();

    void brewCoffee();

    void turnOff();
}
