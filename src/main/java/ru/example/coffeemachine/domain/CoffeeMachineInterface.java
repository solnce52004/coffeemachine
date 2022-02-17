package ru.example.coffeemachine.domain;

public interface CoffeeMachineInterface {
    void turnOn();

    void checkResources();

    void pushStartBrew();

    void brewCoffee();

    void turnOff();
}
