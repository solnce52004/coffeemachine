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
public class TurnOnCommand implements Command {
    private final CoffeeMachineImpl machine;

    @Override
    public Events getEvent() {
        return Events.PUSH_TURN_ON;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        machine.turnOn();
        log.info("Event: {}", context.getEvent());
    }
}
