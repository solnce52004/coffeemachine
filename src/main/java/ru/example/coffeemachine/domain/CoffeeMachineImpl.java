package ru.example.coffeemachine.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class CoffeeMachineImpl implements CoffeeMachine {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void turnOn() {
        //Thread.sleep(millis);
        //save in db
        //выдать ид см (процессу)
        //сохранить под этим ид стейт
        log.info("do in action on push_turnOn");
    }

    public Boolean isCheckedResources() {
        return true;
    }

    @Override
    public void checkResources() {
        //проверить в бд привязанные к ид см ресурсы
        //есть ли?
        //сколько?
        //по умолчанию в кофеварке 0/0
        //пополнить, если надо 100/100
        //изменить статус - сохранить его в базе
        //сделать связь ид см с ресурсами
    }

    @Override
    public void pushStartBrew() {
        if (isCheckedResources()) {
            // проверяем /гвардом/ что ресурсы пополнены
            // меняем статус см
            // ресурсы заблокировать?
        }
    }

    @Override
    public void brewCoffee() {
        //обнуляем ресурсы 0/0
        //меняем статус
    }

    @Override
    public void turnOff() {
        //меняем статус
        log.info("pushed turnOff");
    }
}
