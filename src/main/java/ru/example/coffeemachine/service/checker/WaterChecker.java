package ru.example.coffeemachine.service.checker;

import java.util.concurrent.Callable;

public class WaterChecker implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        try {
            Thread.sleep(3000);
            //смотрим в базе сколько воды в последней записи для данной СМ
            //если мало - меняем что залили
            //возвращаем статус - полный бак
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
