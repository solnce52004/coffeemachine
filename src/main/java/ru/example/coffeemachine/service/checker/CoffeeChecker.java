package ru.example.coffeemachine.service.checker;

import java.util.concurrent.Callable;

public class CoffeeChecker implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        try {
            Thread.sleep(3000);
            //смотрим в базе сколько в последней записи для данной СМ
            //если мало - меняем что ok
            //возвращаем статус - ok
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
