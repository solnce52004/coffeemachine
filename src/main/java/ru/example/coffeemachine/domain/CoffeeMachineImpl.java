package ru.example.coffeemachine.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CoffeeMachineImpl implements CoffeeMachineInterface {

    private ExecutorService executor;
    @Getter
    @Setter
    private CompletableFuture<Boolean> task;

    @Autowired
    public CoffeeMachineImpl(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void turnOn() {
        this.executor = Executors.newSingleThreadExecutor();
        startTask("Action push_turnOn",300);
    }

    public Boolean isCheckedResources() {
        return true;
    }

    @Override
    public void checkResources() {
        startTask("Action checkResources",2000);
    }

    @Override
    public void pushStartBrew() {
        startTask("Action pushStartBrew",300);
    }

    @Override
    public void brewCoffee() {
        startTask("Action brewCoffee",3000);
    }

    @Override
    public void turnOff() {
        startTask("Action turnOff",100);
        terminateCoffeeMachine();
    }

    //////////////
    private void startTask(String msg, int millis) {
        setTask(CompletableFuture
                .supplyAsync(
                        () -> {
                            try {
                                log.info(msg);
                                Thread.sleep(millis);
                                return true;

                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                            return false;
                        },
                        executor
                )
        );
    }

//    public void afterTask(Consumer<Boolean> action) {
//        getTask().thenAccept(action);
//    }

    private void terminateCoffeeMachine() {
        try {
            executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie) {
            log.error(ie.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdownNow();
        }
    }
}
