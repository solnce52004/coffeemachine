package ru.example.coffeemachine.domain.commnd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.CoffeeMachineImpl;

@Component
@RequiredArgsConstructor
@Slf4j
public class BrewCommand  implements Command {
    private final CoffeeMachineImpl machine;

    @Override
    public Events getEvent() {
        return Events.BREW;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        machine.brewCoffee();
        log.info("Event: {}", context.getEvent());
    }
}