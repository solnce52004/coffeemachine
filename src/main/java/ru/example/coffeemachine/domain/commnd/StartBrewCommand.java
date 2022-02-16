package ru.example.coffeemachine.domain.commnd;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.CoffeeMachineImpl;

@Component
@RequiredArgsConstructor
public class StartBrewCommand implements Command {
    private final CoffeeMachineImpl service;

    @Override
    public Events getEvent() {
        return Events.PUSH_START_BREW;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        service.pushStartBrew();
    }
}