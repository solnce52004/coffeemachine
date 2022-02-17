package ru.example.coffeemachine.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.coffeemachine.config.statemachine.enums.Events;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CoffeeMachineImpl implements CoffeeMachineInterface {

    private ExecutorService executor;
    @Getter @Setter
    private CompletableFuture<Boolean> task;

    @Override
    public void turnOn() {
        this.executor = Executors.newSingleThreadExecutor();
        startTask();
    }

    @Override
    public void checkResources() {
        nextTask("Action " + Events.CHECK_RESOURCES, 2000);
    }

    @Override
    public void pushStartBrew() {
        nextTask("Action " + Events.PUSH_START_BREW, 300);
    }

    @Override
    public void brewCoffee() {
        nextTask("Action " + Events.BREW, 3000);
    }

    @Override
    public void turnOff() {
        nextTask("Action " + Events.PUSH_TURN_OFF, 100);
        terminateCoffeeMachine();
    }

    //////////////
    private void startTask() {
        setTask(
                CompletableFuture.supplyAsync(
                        () -> supplierGet("Action " + Events.PUSH_TURN_ON, 300),
                        executor
                )
        );
    }

    public void nextTask(String msg, int millis) {
        getTask().thenCompose(
                (prevTaskResult) ->
                        CompletableFuture.supplyAsync(
                                () -> {
                                    if (prevTaskResult && executor != null) {
                                        return supplierGet(msg, millis);
                                    }
                                    return false;
                                },
                                executor
                        )
        );
    }

    private Boolean supplierGet(String msg, int millis) {
        try {
            log.info(msg);
            final long id = Thread.currentThread().getId();
            log.info("currentThread {}", id);

            Thread.sleep(millis);
            return true;

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    private void terminateCoffeeMachine() {
        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie) {
            log.error(ie.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdownNow();
        }
    }
}
