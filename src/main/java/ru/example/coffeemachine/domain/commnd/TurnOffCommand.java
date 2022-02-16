package ru.example.coffeemachine.domain.commnd;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.CoffeeMachineImpl;

@Component
@RequiredArgsConstructor
public class TurnOffCommand implements Command {
    private final CoffeeMachineImpl service;

    @Override
    public Events getEvent() {
        return Events.PUSH_TURN_OFF;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        //Thread.sleep(millis);
        //select from db
        //save in db
        //set info in StateContext
        service.turnOff();
    }
}