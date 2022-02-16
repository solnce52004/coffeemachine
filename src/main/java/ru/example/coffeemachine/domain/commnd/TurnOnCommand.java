package ru.example.coffeemachine.domain.commnd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;
import ru.example.coffeemachine.config.statemachine.enums.Events;
import ru.example.coffeemachine.config.statemachine.enums.States;
import ru.example.coffeemachine.domain.CoffeeMachineImpl;

@Component
@Slf4j
public class TurnOnCommand implements Command {
    private final CoffeeMachineImpl service;

    @Autowired
    public TurnOnCommand(CoffeeMachineImpl service) {
        this.service = service;
    }

    @Override
    public Events getEvent() {
        return Events.PUSH_TURN_ON;
    }

    @Override
    public void execute(StateContext<States, Events> context) {
        //Thread.sleep(millis);
        //select from db
        //save in db
        service.turnOn();

        //set info in StateContext
        log.info("Event: {}", context.getEvent());
        context.getExtendedState()
                .getVariables()
                .put("current_state", context.getStage());
    }
}
